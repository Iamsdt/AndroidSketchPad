/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:19 PM
 */

package com.iamsdt.androidsketchpad.ui.bookmark

import android.app.Application
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.ui.details.DetailsActivity
import com.iamsdt.androidsketchpad.ui.main.MainAdapter.Companion.DIFF_CALLBACK
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.iamsdt.androidsketchpad.utils.ext.show
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.bookmark_list.view.*
import kotlinx.android.synthetic.main.main_list_item.view.*
import kotlinx.android.synthetic.main.undo_layout.view.*
import timber.log.Timber
import java.util.HashMap
import kotlin.collections.ArrayList

/*
This adapter can work with live data

 */


class BookmarkAdapter(author: String,
                      private val postTableDao: PostTableDao,
                      context: Application) :
        PagedListAdapter<PostTable, BookmarkAdapter.BookVH>(DIFF_CALLBACK) {


    private val pendingItemRemoval = 3000 // 3sec
    private val handler = Handler() // hanlder for running delayed runnables
    private val pendingRunables: MutableMap<PostTable?, Runnable> = HashMap() // map of items to pending runnables, so we can cancel a removal if need be

    private var itemsPendingRemoval: ArrayList<PostTable?> = ArrayList()

    val author = "By $author"

    private var context: Context = context.baseContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bookmark_list, parent, false)
        return BookVH(view)
    }

    private fun undoOpt(postTable: PostTable?) {
        val pendingRemovalRunnable = pendingRunables[postTable]
        pendingRunables.remove(postTable)
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable)
        itemsPendingRemoval.remove(postTable)
        // this will rebind the row in "normal" state

        val id = currentList?.indexOf(postTable) ?: 0

        notifyItemChanged(id)
    }

    override fun onBindViewHolder(holder: BookVH, position: Int) {
        try {
            val model: PostTable? = getItem(position)

            if (itemsPendingRemoval.contains(model)) {
                holder.regular.gone()
                holder.swipe.show()

                holder.undo.setOnClickListener({ undoOpt(model) })

            } else {
                holder.swipe.gone()
                holder.regular.show()

                holder.bind(model)

                holder.itemView.tag = model
            }

            holder.titleTV.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, model?.id)
                context.startActivity(intent)
            }

            holder.bookmarkImg.setOnClickListener {
                deletePost(model)
            }

        } catch (e: Exception) {
            Timber.e(e, "$position ${currentList?.size}")
        }
    }

    private fun deletePost(model: PostTable?) {
        val thread = HandlerThread("Bookmark")
        thread.start()
        Handler(thread.looper).post({
            if (model != null) {
                //book mark
                val delete = postTableDao.deleteBookmark(model.id)

                Handler(Looper.getMainLooper()).post({
                    if (delete > 0) {
                        Toasty.warning(context, "Bookmark deleted", Toast.LENGTH_SHORT, true).show()
                        //holder.bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark))
                        if (itemsPendingRemoval.contains(model)) {
                            itemsPendingRemoval.remove(model)
                        }
                    }
                })
            }

            thread.quitSafely()
        })
    }

    fun pendingRemoval(position: Int) {

        val data: PostTable? = getItem(position)
        if (!itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.add(data)
            // this will redraw row in "undo" state
            notifyItemChanged(position)
            // let's create, store and post a runnable to remove the data
            val pendingRemovalRunnable = Runnable {
                remove(currentList?.indexOf(data) ?: 0)
            }
            handler.postDelayed(pendingRemovalRunnable, pendingItemRemoval.toLong())
            pendingRunables[data] = pendingRemovalRunnable
        }
    }

    private fun remove(position: Int) {
        val data = currentList?.get(position)

        if (itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.remove(data)
        }

        if (currentList?.contains(data) == true) {
            deletePost(data)
        }
    }

    fun isPendingRemoval(position: Int): Boolean {
        val data = currentList?.get(position)
        return itemsPendingRemoval.contains(data)
    }

    //must change context to avoid crash
    fun changeContext(context: Context) {
        this.context = context
        Timber.i("Change context to activity context")
    }

    inner class BookVH(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.list_img
        val titleTV: TextView = view.title
        private val authorTV: TextView = view.author
        private val dateTV: TextView = view.date
        val bookmarkImg: ImageView = view.bookmark
        private val labelTV: TextView = view.labelTV
        val regular: View = view.regular_layout
        val swipe: View = view.swipeLayout
        val delete: TextView = view.undo_deleteTxt
        val undo: TextView = view.undoBtn

        fun bind(post: PostTable?) {
            val url = post?.imgUrl?.get(0)?.url ?: ""
            if (url.isNotEmpty()) {
                Glide.with(context).load(url).into(image)
            }

            titleTV.text = post?.title
            authorTV.text = author
            val date = DateUtils.getReadableDate(post?.published)
            if (date.isNotEmpty()) {
                dateTV.text = date
            } else dateTV.gone()

            val labels = post?.labels ?: emptyList()
            if (labels.isNotEmpty()) {
                labelTV.text = labels[0]
            }

            if (post?.bookmark == true) {
                bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_done))
            }
        }
    }
}