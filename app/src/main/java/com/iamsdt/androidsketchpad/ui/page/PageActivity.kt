/*
 * Created by Shudipto Trafder
 * on 6/12/18 7:05 PM
 */

package com.iamsdt.androidsketchpad.ui.page

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_page.*
import javax.inject.Inject


class PageActivity:AppCompatActivity(){

    @Inject
    lateinit var pageTableDao: PageTableDao

    @Inject
    lateinit var dataLayer: RemoteDataLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_page)
        setSupportActionBar(toolbar)

        val adapter = PageAdapter(this)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        pageRcv.layoutManager = manager
        pageRcv.adapter = adapter

        pageShimmer.startShimmerAnimation()

        pageTableDao.getAllPage.observe(this, Observer {
            if (it == null || it.isEmpty()){
                if (ConnectivityChangeReceiver.getInternetStatus(this))
                    dataLayer.getPageDetails()
                else
                    showToast(ToastType.ERROR,"No internet available to fetch data",
                            Toast.LENGTH_LONG)
            } else{
                pageShimmer.stopShimmerAnimation()
                pageShimmer.gone()
                adapter.submitList(it)
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}