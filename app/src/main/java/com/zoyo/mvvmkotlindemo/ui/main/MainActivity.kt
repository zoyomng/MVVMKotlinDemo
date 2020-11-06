package com.zoyo.mvvmkotlindemo.ui.main

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.BaseActivity
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.core.utils.LogUtil
import com.zoyo.mvvmkotlindemo.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity :
    BaseActivity<MainActivityBinding>(R.layout.main_activity, BR.viewModel) {

    private val mainViewModel: MainViewModel by viewModels()
    override fun getVM(): BaseViewModel {
        return mainViewModel
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    public lateinit var navController: NavController

    override fun initData() {

        navController = findNavController(R.id.nav_host_fragment)
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
            if (destination.id == R.id.navigation_search) {
                bottomNavigationView.visibility = View.GONE
            } else {
                toolBar.visibility = View.VISIBLE
                bottomNavigationView.visibility = View.VISIBLE
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)

        //搜索控件
        val searchItem = menu?.findItem(R.id.action_search)

        val searchView = (searchItem?.actionView as SearchView).apply {

            queryHint = "你想要的这里都有"
            //true-代表在内部显示，false-代表在外部显示
//            setIconifiedByDefault(false)

            setOnSearchClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //点击"搜索"按钮,SearchView控件开始展开
                    //可以切换搜索界面(fragment)
                    LogUtil.e("SearchView-OnSearchClick")
                    navController.navigate(R.id.action_global_navigation_search)
                }
            })
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    LogUtil.e("SearchView-onQueryTextSubmit")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    LogUtil.e("SearchView-onQueryTextChange  $query")
                    return false
                }
            })

            setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    //关闭搜索界面,切换列表页面(fragment)
                    LogUtil.e("SearchView-onClose")
                    navController.navigateUp()
                    return false
                }
            })


        }

        //操作添加器
        val shareItem = menu.findItem(R.id.action_share)
        val shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            setType("image/*")
        }
        shareActionProvider.setShareIntent(shareIntent)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
//            TransitionManager.beginDelayedTransition(toolBar as ViewGroup)
//            item.expandActionView()
            true
        }

        R.id.action_settings -> {
            true
        }

        R.id.action_share -> {


            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    /**
     * 将back键点击事件委托出去,如果当前Fragment不是在最顶端,则返回上一个Fragment
     *
     * @return
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}