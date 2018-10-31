package com.skosc.tkffintech.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.skosc.tkffintech.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
    }
}
