package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import com.skosc.tkffintech.R

class MainActivity : AppCompatActivity() {
    val navController by lazy { findNavController(nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(navigation, navController)
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }
}
