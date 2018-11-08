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
import com.skosc.tkffintech.viewmodel.events.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TKFActivity(), SearchViewProvider {
    companion object {
        private const val UNKNOWN_DESTINATION = 0
    }

    val navController by lazy { findNavController(nav_host_fragment) }
    val vm by lazy { getViewModel(MainActivityViewModel::class) }
    private var searchMenuItem: MenuItem? = null

    // TODO Should i really check for nullability?
    override val searchView: SearchView
        get() {
            val actionView = searchMenuItem?.actionView
            return actionView as SearchView
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(navigation, navController)

        vm.isLoggedIn.observe(this, Observer {
            if (!it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })

        navController.addOnNavigatedListener { _, destination ->
            updateSearchBarVisibility(destination)
            updateAppBarVisibility(destination)
        }
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        searchMenuItem = menu?.findItem(R.id.app_bar_search)
        updateSearchBarVisibility()
        return true
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
}
