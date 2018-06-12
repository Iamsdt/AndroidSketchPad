/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:20 PM
 */

package com.iamsdt.androidsketchpad.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.BLOG_KEY
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.androidsketchpad.utils.model.BlogModel
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.about_blog.*
import kotlinx.android.synthetic.main.activity_aboutblog.*
import javax.inject.Inject

class AboutBlogActivity : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    @Inject
    lateinit var remoteDataLayer: RemoteDataLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_aboutblog)
        setSupportActionBar(toolbar)

        //complete fix if blog empty
        if (spUtils.checkBlogData()) {
            updateUI()
        } else {
            if (ConnectivityChangeReceiver.getInternetStatus(this)) {
                remoteDataLayer.getBlogDetails()
            } else {
                showToast(ToastType.ERROR, "No internet available to fetch data",
                        Toast.LENGTH_LONG)
            }
        }

        remoteDataLayer.layerUtils.uiLIveEvent.observe(this, Observer {
            if (it?.key == BLOG_KEY) {
                if (it.status == 1)
                    updateUI()
                else {
                    if (ConnectivityChangeReceiver.getInternetStatus(this))
                        remoteDataLayer.getBlogDetails()

                }
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateUI() {
        val blog = spUtils.getBlog()
        toolbar.title = blog.name
        nameTv.text = blog.name
        desTV.text = blog.des

        val pub = "Live since ${DateUtils.getReadableDate(blog.published)}"
        publishedTv.text = pub

        val post = "Total post : ${blog.post}"
        postCount.text = post

        val page = "Total Page : ${blog.page}"
        pageCount.text = page

        val up = "Last update : ${blog.update}"
        updateTV.text = up
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}