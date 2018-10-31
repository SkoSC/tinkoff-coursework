package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import com.skosc.tkffintech.R

class MainActivity : AppCompatActivity() {
    val navController by lazy { findNavController(nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_course -> navController.navigate(R.id.navg_coursesFragment)
            R.id.navigation_event -> navController.navigate(R.id.navg_eventsFragment)
            R.id.navigation_profile -> navController.navigate(R.id.navg_profileFragment)
        }
        true
    }
}
