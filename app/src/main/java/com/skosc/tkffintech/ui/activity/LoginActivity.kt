package com.skosc.tkffintech.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
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
            when(status) {
                LoginViewModel.LoginStatus.SUCCESS ->
                    startActivity(Intent(this, MainActivity::class.java))
            }
        })
    }
}
