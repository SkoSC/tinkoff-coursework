package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import com.skosc.tkffintech.R
import com.skosc.tkffintech.viewmodel.events.MainActivityViewModel

class MainActivity : TKFActivity() {
    val navController by lazy { findNavController(nav_host_fragment) }
    val vm by lazy { getViewModel(MainActivityViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(navigation, navController)

        vm.isLoggedIn.observe(this, Observer {
            // TODO Add actual check
            //if (!it) startActivity(Intent(this, LoginActivity::class.java))
        })

        navController.addOnNavigatedListener { _, destination ->
            when (destination.id) {
                R.id.navigation_profile -> supportActionBar?.hide()
                R.id.navigation_event_detail -> supportActionBar?.hide()
                else -> supportActionBar?.show()
            }
        }

    }

    override fun onBackPressed() {
        navController.navigateUp()
    }
}
