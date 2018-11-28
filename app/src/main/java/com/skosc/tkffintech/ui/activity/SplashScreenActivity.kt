package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.Waiter
import com.skosc.tkffintech.utils.extensions.navigateTo
import com.skosc.tkffintech.viewmodel.splash.SplashScreenViewModel
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * Screen that is shown to user, when he opens application with 'cold' start.
 * Provides no useful features to user, but allows internal work to be done silently.
 */
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
}
