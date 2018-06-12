/*
 * Created by Shudipto Trafder
 * on 6/10/18 3:40 PM
 */

package com.iamsdt.androidsketchpad.ui.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.AboutApp
import com.iamsdt.androidsketchpad.ui.AboutBlogActivity
import com.iamsdt.androidsketchpad.ui.DeveloperActivity
import com.iamsdt.androidsketchpad.ui.bookmark.BookmarkActivity
import com.iamsdt.androidsketchpad.ui.page.PageActivity
import com.iamsdt.androidsketchpad.ui.search.SearchActivity
import com.iamsdt.androidsketchpad.ui.services.UpdateService
import com.iamsdt.androidsketchpad.ui.settings.SettingsActivity
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.POST_KEY
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.androidsketchpad.utils.ext.toNextActivity
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import com.iamsdt.themelibrary.ColorActivity
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var bus: EventBus

    @Inject
    lateinit var adapter: MainAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MainVM by lazy {
        ViewModelProviders.of(this, factory).get(MainVM::class.java)
    }

    private val themeRequestCode = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_main)

        adapter.changeContext(this)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        mainRcv.layoutManager = manager
        mainRcv.setDemoLayoutManager(ShimmerRecyclerView.LayoutMangerType.LINEAR_VERTICAL)
        mainRcv.adapter = adapter
        mainRcv.showShimmerAdapter()


        viewModel.getPostData().observe(this, Observer {
            if (it == null || it.size <= 0) {
                //no data in database request data
                if (ConnectivityChangeReceiver.getInternetStatus(this@MainActivity))
                    viewModel.remoteDataLayer.getPostDetailsForFirstTime(false)
                else {
                    fetchFirstPost = true
                    showToast(ToastType.ERROR, "No Internet to fetch data", Toast.LENGTH_LONG)
                }
            } else {
                fetchFirstPost = false
                mainRcv.hideShimmerAdapter()
                adapter.submitList(it)
                Timber.i("Submit list size:${it.size}")
            }
        })

        viewModel.uiLiveData.observe(this, Observer {
            if (it != null && it.key == POST_KEY) {
                if (it.status == 1) {
                    //open for new request
                    isRequested = false
                    waitForNetwork = false
                    if (!postRequestComplete) {
                        viewModel.nextPost()
                        postRequestComplete = true
                    }

                } else {
                    viewModel.nextPost()
                    isRequested = true
                }

            }
        })

        mainRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = manager.itemCount
                val lastVisible = manager.findLastVisibleItemPosition()

                val endHasBeenReached = lastVisible + 4 >= totalItemCount

                if (totalItemCount >= 20 && endHasBeenReached) {
                    if (!isRequested) {
                        if (ConnectivityChangeReceiver.getInternetStatus(this@MainActivity))
                            viewModel.requestNewPost()
                        else
                            waitForNetwork = true
                    }
                }

            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_bookmark -> {
                toNextActivity(BookmarkActivity::class)
            }

            R.id.nav_search -> {
                toNextActivity(SearchActivity::class)
            }

            R.id.nav_page -> {
                toNextActivity(PageActivity::class)
            }

            R.id.nav_setting -> {
                toNextActivity(SettingsActivity::class)
            }

            R.id.nav_choseColor -> {
                ColorActivity.hideNightModeIcon()
                startActivityForResult(ColorActivity.createIntent(this), themeRequestCode)
            }

            R.id.nav_about -> {
                toNextActivity(AboutBlogActivity::class)
            }

            R.id.nav_about_app -> {
                toNextActivity(AboutApp::class)
            }

            R.id.nav_developer -> {
                toNextActivity(DeveloperActivity::class)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == themeRequestCode && resultCode == Activity.RESULT_OK) {
            //recreate this activity
            recreate()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun busEvent(eventMessage: EventMessage) {
        if (eventMessage.key == ConstantUtils.internet) {
            if (eventMessage.message == ConstantUtils.connected) {
                showToast(ToastType.SUCCESSFUL, "Network connected")
                if (waitForNetwork)
                    viewModel.requestNewPost()

                if (fetchFirstPost)
                    viewModel.remoteDataLayer.getPostDetailsForFirstTime(false)
            } else {
                showToast(ToastType.WARNING, "No Internet")
            }
        }
    }


    //register and unregister event bus
    override fun onStart() {
        super.onStart()

        if (!bus.isRegistered(this)) {
            bus.register(this@MainActivity)
        }

        if (UpdateService.isRunningService && UpdateService.isComplete) {
            stopService(Intent(this, UpdateService::class.java))
            Timber.i("Service complete, stopping service")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bus.isRegistered(this)) {
            bus.unregister(this@MainActivity)
        }
    }

    companion object {
        var fetchFirstPost = false
        var postRequestComplete = false
        var isRequested = false
        var waitForNetwork = false
    }

}
