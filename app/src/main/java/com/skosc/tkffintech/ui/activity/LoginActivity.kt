package com.skosc.tkffintech.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.skosc.tkffintech.R
import com.skosc.tkffintech.utils.GlobalConstants
import com.skosc.tkffintech.utils.extensions.navigateTo
import com.skosc.tkffintech.utils.extensions.observe
import com.skosc.tkffintech.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Provides login functionality to user.
 */
class LoginActivity : TKFActivity() {
    private val vm by lazy { getViewModel(LoginViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupInteractionCallbacks()
        bindDataToViewModel()
    }

    private fun bindDataToViewModel() {
        vm.dataIsLoading.observe(this, this::updateLoginButton)
        vm.error.observe(this, this::showError)
        vm.navigateToMain.observe(this, navigateTo(MainActivity::class))
    }

    private fun setupInteractionCallbacks() {
        login_register.setOnClickListener(this::launchBrowserWithRegisterPage)
        login_btn.setOnClickListener(this::startLoggingIn)
    }

    private fun startLoggingIn(v: View) {
        val (email, password) = readUserCredentials()
        vm.login(email, password)
    }

    private fun readUserCredentials(): Pair<String, String> {
        val email = email_et.text.toString()
        val password = password_et.text.toString()
        return Pair(email, password)
    }

    private fun launchBrowserWithRegisterPage(v: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, GlobalConstants.URL.TINKOFF_REGISTER.toUri())
        startActivity(browserIntent)
    }

    private fun updateLoginButton(dataIsLoading: Boolean) {
        if (dataIsLoading) {
            turnLoadingMode()
        } else {
            turnWaitingMode()
        }
    }

    private fun turnWaitingMode() {
        login_loading_status.visibility = View.GONE
        login_btn.text = getString(R.string.login_login_btn)
        login_btn.isEnabled = true
    }

    private fun turnLoadingMode() {
        login_loading_status.visibility = View.VISIBLE
        login_btn.text = ""
        login_btn.isEnabled = false
    }

    private fun showError(error: LoginViewModel.Error?) {
        // Ignore empty errors
        if (error == null) return

        AlertDialog.Builder(this)
                .setTitle(R.string.login_error_title)
                .setMessage(error.text)
                .create()
                .show()
    }

    override fun onBackPressed() {}
}
