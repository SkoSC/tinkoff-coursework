package com.skosc.tkffintech.ui.activity

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

        vm.status.observe(this, Observer { status ->
            when (status) {
                LoginViewModel.LoginStatus.SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                LoginViewModel.LoginStatus.IN_PROGRESS -> {
                    login_loading_status.visibility = View.VISIBLE
                    login_btn.text = ""
                }
                LoginViewModel.LoginStatus.WAITING -> {
                    login_loading_status.visibility = View.GONE
                    login_btn.text = getString(R.string.login_login_btn)
                }
                LoginViewModel.LoginStatus.ERROR -> {
                    login_loading_status.visibility = View.GONE
                    login_btn.text = getString(R.string.login_login_btn)
                }
            }
        })
    }
}
