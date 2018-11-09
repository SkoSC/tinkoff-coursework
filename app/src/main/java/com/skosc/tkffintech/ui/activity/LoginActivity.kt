package com.skosc.tkffintech.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.skosc.tkffintech.R
import com.skosc.tkffintech.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : TKFActivity() {
    private val vm by lazy { getViewModel(LoginViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            val email = email_et.text.toString()
            val password = password_et.text.toString()
            vm.login(email, password)
        }

        vm.status.observe(this, Observer(this::showLoginStatus))
    }

    private fun showLoginStatus(status: LoginViewModel.Status) {
        return when (status) {
            is LoginViewModel.Status.Success-> {
                startActivity(
                        Intent(this, MainActivity::class.java)
                )
            }
            is LoginViewModel.Status.InProgress -> {
                login_loading_status.visibility = View.VISIBLE
                login_btn.text = ""
            }
            is LoginViewModel.Status.Waiting -> {
                login_loading_status.visibility = View.GONE
                login_btn.text = getString(R.string.login_login_btn)
            }
            is LoginViewModel.Status.Error -> {
                login_loading_status.visibility = View.GONE
                login_btn.text = getString(R.string.login_login_btn)
                showError(status.error ?: LoginViewModel.LoginError.UNKNOWN)
            }
        }
    }

    private fun showError(error: LoginViewModel.LoginError) {
        val errText = when (error) {
            LoginViewModel.LoginError.UNKNOWN -> R.string.login_error_wrong_unknown
            LoginViewModel.LoginError.WRONG_CREDENTIALS -> R.string.login_error_wrong_credentials
        }

        AlertDialog.Builder(this)
                .setTitle(R.string.login_error_title)
                .setMessage(errText)
                .create()
                .show()
    }
}
