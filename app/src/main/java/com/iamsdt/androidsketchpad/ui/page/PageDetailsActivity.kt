/*
 * Created by Shudipto Trafder
 * on 6/12/18 9:14 PM
 */

package com.iamsdt.androidsketchpad.ui.page

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.utils.HtmlHelper
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import com.iamsdt.androidsketchpad.utils.ext.customTab
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.iamsdt.androidsketchpad.utils.ext.show
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_page_details.*
import javax.inject.Inject


class PageDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var pageTableDao: PageTableDao

    private lateinit var id: String

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: PageDetailsVM by lazy {
        ViewModelProviders.of(this, factory).get(PageDetailsVM::class.java)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_page_details)
        setSupportActionBar(toolbar)

        id = intent.getStringExtra(Intent.EXTRA_HTML_TEXT) ?: ""

        page_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest): Boolean {

                //open external link in google chrome custom tab
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    customTab(request.url.toString())
                } else {
                    customTab(request.url.toString())
                }
                return true
            }

            //open external link in google chrome
            //using deprecated method
            // some time above method is not call
            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                customTab(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showMainView()
            }
        }

        showLoading()

        //web view settings
        val settings = page_webview.settings
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        settings.loadWithOverviewMode = true
        settings.loadsImagesAutomatically = true
        settings.javaScriptEnabled = true

        page_webview.setPadding(0, 0, 0, 0)

        viewModel.getSinglePage(id).observe(this, Observer {
            if (it != null) {
                page_webview.loadData(it.content,
                        "text/html", "UTF-8")

                page_title.text = it.title
            }
        })


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showMainView() {
        page_main.show()
        page_loading.gone()
    }

    private fun showLoading() {
        page_main.gone()
        page_loading.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}