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
import android.view.MenuItem
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.services.UpdateService
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.POST_KEY
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import com.iamsdt.themelibrary.ColorActivity
import com.iamsdt.themelibrary.ThemeUtils
import dagger.internal.DelegateFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var bus: EventBus

    @Inject
    lateinit var adapter: MainAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel:MainVM by lazy {
        ViewModelProviders.of(this,factory).get(MainVM::class.java)
    }

    private val themeRequestCode = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_main)

        adapter.changeContext(this)

        mainRcv.layoutManager = LinearLayoutManager(this)
        mainRcv.adapter = adapter

        viewModel.getPostData().observe(this, Observer {
            if (it == null || it.size <= 0){
                //no data in database request data
                viewModel.remoteDataLayer.getPostDetailsForFirstTime(false)
            } else{
                adapter.submitList(it)
                Timber.i("Submit list size:${it.size}")
                viewModel.requestNewPost(it.size)
            }
        })

        viewModel.uiLiveData.observe(this, Observer {
            if (it != null && it.key == POST_KEY){
                if (it.status == 1 && !postRequestComplete){
                    viewModel.getTokenPost()
                } else{
                    viewModel.getTokenPost()
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
                //startActivity(Intent(this@MainActivity, BookmarkActivity::class.java))
            }

            R.id.nav_categories -> {

            }

            R.id.nav_page -> {

            }

            R.id.nav_setting -> {
                //startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }

            R.id.nav_choseColor -> {
                startActivityForResult(ColorActivity.createIntent(this), themeRequestCode)
            }

            R.id.nav_about -> {

            }

            R.id.nav_copyright -> {
            }

            R.id.nav_tms -> {
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
            } else {
                showToast(ToastType.WARNING, "No Internet")
            }
        }

        if (eventMessage.key == ConstantUtils.Event.SERVICE && eventMessage.status == 1) {
            if (UpdateService.isRunningService) {
                stopService(Intent(this, UpdateService::class.java))
                Timber.i("Service complete, stopping service")
            }
        }
    }


    //register and unregister event bus
    override fun onStart() {
        super.onStart()

        if (!bus.isRegistered(this)) {
            bus.register(this@MainActivity)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (bus.isRegistered(this)) {
            bus.unregister(this@MainActivity)
        }
    }

    companion object {
        var postRequestComplete = false
    }

}
