/*
 * Created by Shudipto Trafder
 * on 6/10/18 5:02 PM
 */

package com.iamsdt.androidsketchpad.ui.main

import android.app.Application
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.main_list_item.view.*
import timber.log.Timber

class MainAdapter(private val picasso: Picasso,
                  author: String,
                  private val postTableDao: PostTableDao,
                  context: Application)
    : PagedListAdapter<PostTable, MainAdapter.VH>(DIFF_CALLBACK) {

    val author = "By $author"

    private var context:Context = context.baseContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return VH(view)
    }

    //must change context to avoid crash
    fun changeContext(context: Context){
        this.context = context
        Timber.i("Change context to activity context")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val model: PostTable? = getItem(position)
        holder.bind(model)

        if (model != null) {
            holder.bookmarkImg.setOnClickListener {
                var set = 0
                var delete = 0

                val thread = HandlerThread("Bookmark")
                thread.start()
                Handler(thread.looper).post({
                    if (model.bookmark) {
                        //book mark
                        delete = postTableDao.deleteBookmark(model.id)
                    } else {
                        set = postTableDao.setBookmark(model.id)
                    }

                    Handler(Looper.getMainLooper()).post({
                        if (set > 0) {
                            Toasty.success(context, "Bookmarked", Toast.LENGTH_SHORT, true).show()
                            holder.bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_done))
                        }

                        if (delete > 0) {
                            Toasty.warning(context, "Bookmark deleted", Toast.LENGTH_SHORT, true).show()
                            holder.bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark))
                        }
                    })

                    thread.quitSafely()
                })
            }
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.list_img
        private val titleTV: TextView = view.title
        private val authorTV: TextView = view.author
        private val dateTV: TextView = view.date
        val bookmarkImg: ImageView = view.bookmark

        fun bind(post: PostTable?) {
            val url = post?.imgUrl?.get(0)?.url ?: ""
            if (url.isNotEmpty()) {
                picasso.load(url).fit().into(image)
            } else {
                image.gone()
            }

            titleTV.text = post?.title
            authorTV.text = author
            val date = DateUtils.getReadableDate(post?.published)
            if (date.isNotEmpty()) {
                dateTV.text = date
            } else dateTV.gone()
        }
    }

    private fun showToast(message: String) {

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostTable>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldConcert: PostTable,
                                         newConcert: PostTable): Boolean =
                    oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: PostTable,
                                            newConcert: PostTable): Boolean =
                    oldConcert == newConcert
        }
    }
}