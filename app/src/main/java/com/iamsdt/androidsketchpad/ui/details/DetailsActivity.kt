/*
 * Created by Shudipto Trafder
 * on 6/11/18 12:02 AM
 */

package com.iamsdt.androidsketchpad.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import com.iamsdt.androidsketchpad.utils.ext.customTab
import kotlinx.android.synthetic.main.content_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    private lateinit var id: String

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: DetailsVM by lazy {
        ViewModelProviders.of(this, factory).get(DetailsVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        id = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""

        webView?.webViewClient = object : WebViewClient() {
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
        }

        //web view settings
        val settings = webView.settings
        //app web view catching
        // TODO: 6/11/2018 add settings
        //by default it false
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        settings.allowContentAccess = false
        settings.loadWithOverviewMode = true

        viewModel.getDetails(id).observe(this, Observer {
            webView.loadData(it?.content, "text/html", "UTF-8")
        })

    }
}