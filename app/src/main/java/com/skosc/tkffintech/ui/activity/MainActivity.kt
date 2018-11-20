package com.skosc.tkffintech.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.contracts.SearchViewProvider
import com.skosc.tkffintech.viewmodel.main.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TKFActivity(), SearchViewProvider {
    companion object {
        private const val UNKNOWN_DESTINATION = 0
    }

    val vm by lazy { getViewModel(MainActivityViewModel::class) }

    val navController by lazy { findNavController(nav_host_fragment) }
    private var searchMenuItem: MenuItem? = null

    override val searchView: SearchView
        get() = searchMenuItem?.actionView as? SearchView
                ?: throw IllegalStateException("Search menu item not set")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(navigation, navController)

        vm.isLoggedIn.observe(this, Observer { isLoggedIn ->
            if (!isLoggedIn) {
                startActivity(Intent(this, LoginActivity::class.java))
            }


        })

        navController.addOnNavigatedListener { _, destination ->
            updateSearchBarVisibility(destination)
            updateAppBarVisibility(destination)
            updateBackButtonVisibility(destination)
        }
    }

    override fun onBackPressed() {
        // TODO Add more elegant way to handle exiting application
        val success = navController.navigateUp()
        if (!success) {
            finishAndRemoveTask()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        searchMenuItem = menu?.findItem(R.id.app_bar_search)
        updateSearchBarVisibility()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateSearchBarVisibility(destination: NavDestination? = navController.currentDestination) {
        searchMenuItem?.let { item ->
            item.isVisible = when (destination?.id ?: UNKNOWN_DESTINATION) {
                R.id.navigation_event_list -> true
                else -> false
            }
        }
    }

    private fun updateAppBarVisibility(destination: NavDestination? = navController.currentDestination) {
        when (destination?.id ?: UNKNOWN_DESTINATION) {
            R.id.navigation_profile -> supportActionBar?.hide()
            R.id.navigation_event_detail -> supportActionBar?.hide()
            else -> supportActionBar?.show()
        }
    }

    private fun updateBackButtonVisibility(destination: NavDestination? = navController.currentDestination) {
        val isEnabled = when (destination?.id ?: UNKNOWN_DESTINATION) {
            R.id.navigation_event_list -> true
            else -> false
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
    }
}
