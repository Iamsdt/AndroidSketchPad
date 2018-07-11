/*
 * Created by Shudipto Trafder
 * on 6/12/18 10:31 PM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.POST_KEY
import com.iamsdt.androidsketchpad.utils.ext.*
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_list.*
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

    private var document: String = ""

    private var suggestions: SearchRecentSuggestions? = null

    // complete: 6/13/2018 add search button on toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_toolbar)

        adapter.changeContext(this)


        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        mainRcv.layoutManager = manager
        mainRcv.adapter = adapter

        shimmerLayout.gone()

        if (!ConnectivityChangeReceiver.getInternetStatus(this))
            showToast(ToastType.ERROR, "No internet to search", Toast.LENGTH_LONG)


        viewModel.searchData.observe(this, Observer {
            if (it?.items?.isNotEmpty() == true) {
                shimmerLayout.stopShimmerAnimation()
                shimmerLayout.gone()
                adapter.submitList(it.items)
                document = ""
            }
        })

        viewModel.searchEvent.observe(this, Observer {
            if (it?.key == POST_KEY) {
                if (it.status == 1 && it.message == "0") {
                    showToast(ToastType.INFO, "No data found")
                    document = ""
                } else if (it.status == 0) {
                    if (ConnectivityChangeReceiver.getInternetStatus(this)
                            && document.isNotEmpty())
                        viewModel.requestSearch(document)
                }
            }
        })

        //set suggestion option
        suggestions = SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
        handleSearch(intent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecentQuery(query: String) {
        suggestions?.saveRecentQuery(query, null)

    }

    override fun onNewIntent(intent: Intent) {
        handleSearch(intent)
    }

    private fun handleSearch(intent: Intent){
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            // complete: 6/14/2018 search
            shimmerLayout.show()
            shimmerLayout.startShimmerAnimation()
            mainRcv.removeAllViews()
            viewModel.requestSearch(query)
            setRecentQuery(query)
            document = query

            //Search
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Search Data")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "User search query")
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Search $query")
            FirebaseAnalytics.getInstance(this)
                    .logEvent(FirebaseAnalytics.Event.SEARCH,bundle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val searchView:SearchView = menu?.findItem(R.id.search)?.actionView as SearchView
        //search
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(true)
        searchView.isQueryRefinementEnabled = true
        searchView.requestFocus()

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val selectedView = searchView.suggestionsAdapter
                val cursor = selectedView.getItem(position) as Cursor
                val index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1)
                searchView.setQuery(cursor.getString(index), true)
                return true
            }
        })

        return true
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