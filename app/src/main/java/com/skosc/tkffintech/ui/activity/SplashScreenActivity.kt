package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.Waiter
import com.skosc.tkffintech.viewmodel.splash.SplashScreenViewModel
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class SplashScreenActivity : TKFActivity() {
    companion object {
        private const val TRANSITION_DELAY_SECONDS = 1L
    }

    private val vm by lazy { getViewModel(SplashScreenViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()

        vm.navigateToLogin.observe(this, navigateTo(LoginActivity::class))
        vm.navigateToMain.observe(this, navigateTo(MainActivity::class))
    }

    /**
     * Navigates to [TKFActivity]
     * @return Closure navigating to passed activity
     */
    private fun <T: TKFActivity> navigateTo(activity: KClass<T>) = {
        Waiter.wait(TRANSITION_DELAY_SECONDS, TimeUnit.SECONDS) {
            val intent = Intent(this, activity.java)
            startActivity(intent)
        }
    }
}
