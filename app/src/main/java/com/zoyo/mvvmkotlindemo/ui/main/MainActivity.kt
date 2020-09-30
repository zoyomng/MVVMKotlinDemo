package com.zoyo.mvvmkotlindemo.ui.main

import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseActivity
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity :
    BaseActivity<MainActivityBinding>(R.layout.main_activity, BR.viewModel) {

    private val mainViewModel: MainViewModel by viewModels()
    override fun getVM(): BaseViewModel {
        return mainViewModel
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initData() {

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        //操作栏上添加导航
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id == R.id.full_screen_destination) {
//                toolbar.visibility = View.GONE
//                bottomNavigationView.visibility = View.GONE
//            } else {
//                toolbar.visibility = View.VISIBLE
//                bottomNavigationView.visibility = View.VISIBLE
//            }
        }
        setupWithNavController(bottomNavigationView, navController)
        setupWithNavController(
            collapsingToolbarLayout,
            toolBar,
            navController,
            appBarConfiguration
        )
        setupWithNavController(toolBar, navController, drawerLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)

        //搜索控件
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }

        }

        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(expandListener)

        //操作添加器
        val shareItem = menu?.findItem(R.id.action_share)
        val shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            setType("image/*")
        }
        shareActionProvider.setShareIntent(shareIntent)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            true
        }

        R.id.action_share -> {


            true
        }

        else -> super.onOptionsItemSelected(item)
    }


}