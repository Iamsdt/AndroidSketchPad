/*
 * Created by Shudipto Trafder
 * on 6/11/18 12:02 AM
 */

package com.iamsdt.androidsketchpad.ui.details

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.HtmlHelper
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.*
import com.iamsdt.themelibrary.ThemeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.author_card.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.loading_layer.*
import org.greenrobot.eventbus.util.ErrorDialogManager.factory
import org.jsoup.Jsoup
import timber.log.Timber
import java.lang.StringBuilder
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: DetailsVM by lazy {
        ViewModelProviders.of(this, factory).get(DetailsVM::class.java)
    }

    private lateinit var id: String

    private lateinit var shareActionProvider: ShareActionProvider

    private lateinit var menuItem: MenuItem

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

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

        settings.loadWithOverviewMode = true
        settings.loadsImagesAutomatically = true
        settings.javaScriptEnabled = true

        webView.setPadding(0, 0, 0, 0)

        viewModel.getDetails(id).observe(this, Observer {
            if (it != null) {
                webView.loadData(HtmlHelper.getHtml(it.content),
                        "text/html", "UTF-8")

                titleTV.text = it.title
                setAuthor()
                labels.text = getLabel(it.labels)

                resetSap(it.title, it.url)

                if (::menuItem.isInitialized && it.bookmark)
                    menuItem.setIcon(R.drawable.ic_bookmark_done)

            } else {
                //show empty view
                empty_tv.show()
                empty_tv.setOnClickListener {
                    onBackPressed()
                }
            }
        })

        viewModel.singleLiveEvent.observe(this, Observer {
            if (::menuItem.isInitialized) {
                when (it) {
                    BookMark.SET -> {
                        menuItem.setIcon(R.drawable.ic_bookmark_done)
                        showToast(ToastType.SUCCESSFUL, "Bookmarked")
                    }

                    BookMark.DELETE -> {
                        menuItem.setIcon(R.drawable.ic_bookmark)
                        showToast(ToastType.WARNING,"Bookmark removed")
                    }
                }
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getLabel(list: List<String>?): String {
        val sb = StringBuilder()
        for (li in list ?: emptyList()) {
            if (li.isNotEmpty())
                sb.append("$li,")
        }
        val string = sb.toString().replaceBeforeLast(",", "")
        Timber.i("Replace string:$string")

        return string
    }

    private fun setAuthor() {
        val author = spUtils.getAuthor()
        authorName.text = author.displayName
        authorDes.text = author.des
        picasso.load(author.imageUrl).fit().into(authorImg)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details, menu)

        menuItem = menu.findItem(R.id.bookmark)
        menuItem.setIcon(R.drawable.ic_bookmark)

        val shareMenu = menu.findItem(R.id.share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareMenu) as ShareActionProvider
        shareActionProvider.setShareIntent(createShareIntent())
        return super.onCreateOptionsMenu(menu)
    }

    private fun createShareIntent(): Intent? {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val share = "https://androsketchpad.blogspot.com"
        shareIntent.putExtra(Intent.EXTRA_TEXT, share)
        return shareIntent
    }

    private fun resetSap(title: String, link: String) {
        if (::shareActionProvider.isInitialized) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val share = "$title... read more on $link"
            shareIntent.putExtra(Intent.EXTRA_TEXT, share)
            shareActionProvider.setShareIntent(shareIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
        //back to home
            android.R.id.home -> onBackPressed()

            R.id.bookmark ->
                viewModel.requestBookmark()

            R.id.action_settings -> {
                //Settings //toNextActivity()
            }

            R.id.textSize -> {
                //increase text size
            }
        }

        return super.onOptionsItemSelected(item)
    }

}