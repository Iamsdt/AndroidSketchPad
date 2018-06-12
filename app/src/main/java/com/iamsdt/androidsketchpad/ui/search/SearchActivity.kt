/*
 * Created by Shudipto Trafder
 * on 6/12/18 10:31 PM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.main.MainVM
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.POST_KEY
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var bus: EventBus

    @Inject
    lateinit var adapter: SearchAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: SearchVM by lazy {
        ViewModelProviders.of(this, factory).get(SearchVM::class.java)
    }

    var document: String = ""

    // TODO: 6/13/2018 add search button on toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.main_layout)
        setSupportActionBar(toolbar)

        adapter.changeContext(this)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        mainRcv.layoutManager = manager
        mainRcv.setDemoLayoutManager(ShimmerRecyclerView.LayoutMangerType.LINEAR_VERTICAL)
        mainRcv.adapter = adapter
        mainRcv.showShimmerAdapter()

        if (!ConnectivityChangeReceiver.getInternetStatus(this))
            showToast(ToastType.ERROR,"No internet to search", Toast.LENGTH_LONG)


        viewModel.searchData.observe(this, Observer {
            if (it?.items?.isNotEmpty() == true) {
                mainRcv.hideShimmerAdapter()
                adapter.submitList(it.items)
            }
        })

        viewModel.searchEvent.observe(this, Observer {
            if (it?.key == POST_KEY) {
                if (it.status == 1 && it.message == "0") {
                    showToast(ToastType.INFO, "No data found")
                } else if (it.status == 0) {
                    if (ConnectivityChangeReceiver.getInternetStatus(this)
                            && document.isNotEmpty())
                        viewModel.requestSearch(document)
                }
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
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
    }


    //register and unregister event bus
    override fun onStart() {
        super.onStart()

        if (!bus.isRegistered(this)) {
            bus.register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bus.isRegistered(this)) {
            bus.unregister(this)
        }
    }
}