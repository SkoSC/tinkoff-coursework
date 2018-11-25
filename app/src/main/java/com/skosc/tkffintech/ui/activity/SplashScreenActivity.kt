package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.Waiter
import com.skosc.tkffintech.viewmodel.splash.SplashScreenViewModel
import java.util.concurrent.TimeUnit

class SplashScreenActivity : TKFActivity() {
    companion object {
        private const val TRANSITION_DELEY_SECONDS = 1
    }

    private val vm by lazy { getViewModel(SplashScreenViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        vm.isLoggedIn.observe(this, Observer { isLoggedIn ->
            val intent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }

            Waiter.wait(1, TimeUnit.SECONDS) {
                startActivity(intent)
            }
        })
    }
}
