/*
 * Created by Shudipto Trafder
 * on 6/12/18 10:33 PM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bumptech.glide.Glide
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.retrofit.model.common.Author
import com.iamsdt.androidsketchpad.data.retrofit.model.singlePost.SinglePostResponse
import com.iamsdt.androidsketchpad.ui.settings.SettingsActivity
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.HtmlHelper
import com.iamsdt.androidsketchpad.utils.ext.*
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.author_card.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.loading_layer.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import java.lang.StringBuilder
import javax.inject.Inject

class SearchDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var bus: EventBus


    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var shareActionProvider: ShareActionProvider

    private lateinit var menuItem: MenuItem

    private var isBookmarked = false

    private val viewModel: SearchDetailsVM by lazy {
        ViewModelProviders.of(this, factory).get(SearchDetailsVM::class.java)
    }

    lateinit var id: String

    private var singlePostResponse:SinglePostResponse ?= null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        id = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
        isBookmarked = intent.getLongExtra(Intent.EXTRA_LOCAL_ONLY,0) != 0L

        if (!ConnectivityChangeReceiver.getInternetStatus(this))
            showToast(ToastType.ERROR, "No internet to fetch", Toast.LENGTH_LONG)


        showLoadingView()
        viewModel.getData(id)

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

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showMainView()
            }
        }

        //web view settings
        val settings = webView.settings
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        settings.loadWithOverviewMode = true
        settings.loadsImagesAutomatically = true
        settings.javaScriptEnabled = true

        webView.setPadding(0, 0, 0, 0)

        viewModel.searchData.observe(this, Observer {
            if (it != null) {

                toolbar.title = it.title

                singlePostResponse = it

                webView.loadData(HtmlHelper.getHtml(it.content),
                        "text/html", "UTF-8")

                titleTV.text = it.title
                labels.text = getLabel(it.labels)

                setAuthor(it.author)

                resetSap(it.title, it.url)

            } else {
                //show empty view
                if (loading != null && empty_tv != null) {
                    empty_tv.show()
                    empty_tv.setOnClickListener {
                        onBackPressed()
                    }
                }
            }

        })

        viewModel.singleLiveEvent.observe(this, Observer {
            if (::menuItem.isInitialized) {
                when (it) {
                    BookMark.SET -> {
                        showToast(ToastType.SUCCESSFUL, "Bookmarked and saved locally")
                        menuItem.setIcon(R.drawable.ic_bookmark_done)
                        isBookmarked = true
                    }
                    else -> {}
                }
            }
        })

        viewModel.searchEvent.observe(this, Observer {
            if (it?.key == ConstantUtils.Event.POST_KEY) {
                if (it.status == 1 && it.message == "0") {
                    showToast(ToastType.INFO, "No data found")
                } else if (it.status == 0) {
                    viewModel.getData(id)
                }
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showLoadingView() {
        detailsView.gone()
        //loading screen
        loading.show()
    }

    private fun showMainView() {
        detailsView.show()
        //loading screen
        loading.gone()
    }

    private fun textIncrease() {
        val settings = webView.settings
        settings.textZoom = settings.textZoom + 10
    }

    private fun getLabel(list: List<String>?): String {
        val sb = StringBuilder()
        for (li in list ?: emptyList()) {
            if (li.isNotEmpty())
                sb.append("$li,")
        }
        val string = sb.toString().replaceAfterLast(",", "")
        Timber.i("Replace string:$string")

        return string
    }

    private fun setAuthor(author: Author) {
        authorName.text = author.displayName
        authorDes.gone()

        val url = author.image.url
        if (url.isNotEmpty()) {
            Glide.with(this).load(url).into(authorImg)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details, menu)

        menuItem = menu.findItem(R.id.bookmark)

        if (isBookmarked) {
            menuItem.setIcon(R.drawable.ic_bookmark_done)
        }

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
                if (!isBookmarked){
                    viewModel.requestBookmark(singlePostResponse)
                }

            R.id.action_settings -> {
                toNextActivity(SettingsActivity::class)
            }

            R.id.textSize -> {
                textIncrease()
            }
        }

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