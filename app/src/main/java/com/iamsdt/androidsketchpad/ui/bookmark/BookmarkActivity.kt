/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:19 PM
 */

package com.iamsdt.androidsketchpad.ui.bookmark

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.main.MainAdapter
import com.iamsdt.androidsketchpad.utils.SwipeUtil
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.iamsdt.androidsketchpad.utils.ext.show
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.actvity_bookmark.*
import javax.inject.Inject

class BookmarkActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: BookmarkAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: BookmarkVM by lazy {
        ViewModelProviders.of(this, factory).get(BookmarkVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.actvity_bookmark)
        setSupportActionBar(toolbar)

        adapter.changeContext(this)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        bookmarkRcv.layoutManager = manager
        bookmarkRcv.adapter = adapter

        emptyView()

        setSwipeForRecyclerView(bookmarkRcv)

        viewModel.getBookmarkData().observe(this, Observer {
            bookmarkView()
            adapter.submitList(it)

            if (it?.size ?: 0 == 0) {
                emptyView()
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun setSwipeForRecyclerView(recyclerView: RecyclerView) {

        val swipeHelper = object : SwipeUtil(0, ItemTouchHelper.START or ItemTouchHelper.END, this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val adapter = recyclerView.adapter as BookmarkAdapter
                adapter.pendingRemoval(swipedPosition)
            }

            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val position = viewHolder.adapterPosition
                val adapter = recyclerView.adapter as BookmarkAdapter
                return if (adapter.isPendingRemoval(position)) {
                    0
                } else super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        val mItemTouchHelper = ItemTouchHelper(swipeHelper)
        mItemTouchHelper.attachToRecyclerView(recyclerView)

        //set swipe label
        swipeHelper.leftSwipeLabel = "Bookmark removed"
        //set swipe background-Color
        swipeHelper.leftColorCode = ContextCompat.getColor(this, R.color.red_300)

    }


    private fun emptyView() {
        bookmarkLay.gone()
        noBookmark.show()
    }

    private fun bookmarkView() {
        bookmarkLay.show()
        noBookmark.gone()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}