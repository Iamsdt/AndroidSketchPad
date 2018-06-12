/*
 * Created by Shudipto Trafder
 * on 6/12/18 10:32 PM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.app.Application
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
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
import com.iamsdt.androidsketchpad.R.id.author
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.ItemsItem
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.ui.details.DetailsActivity
import com.iamsdt.androidsketchpad.ui.main.MainAdapter
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.main_list_item.view.*
import timber.log.Timber

class SearchAdapter(private val picasso: Picasso,
                    private val postTableDao: PostTableDao,
                    context: Application)
    : RecyclerView.Adapter<SearchAdapter.VH>() {


    private var list: List<ItemsItem> = emptyList()

    override fun getItemCount(): Int =
            list.size

    private var context: Context = context.baseContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val model: ItemsItem = list[position]
        holder.bind(model)

        var set: Long = 0

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SearchDetailsActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, model.id)
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, set)
            context.startActivity(intent)
        }

        holder.bookmarkImg.setOnClickListener {


            val thread = HandlerThread("Bookmark")
            thread.start()
            Handler(thread.looper).post({
                val posTable = PostTable(model.id,
                        model.images,
                        model.title,
                        model.published,
                        model.labels,
                        model.content,
                        model.url,
                        true)

                set = postTableDao.add(posTable)

                Handler(Looper.getMainLooper()).post({
                    if (set > 0) {
                        Toasty.success(context, "Bookmarked and saved locally", Toast.LENGTH_SHORT, true).show()
                        holder.bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_done))
                    }
                })

                thread.quitSafely()
            })
        }

    }

    //must change context to avoid crash
    fun changeContext(context: Context) {
        this.context = context
        Timber.i("Change context to activity context")
    }

    fun submitList(list: List<ItemsItem>) {
        this.list = list
        //strong possibilities of different data
        notifyDataSetChanged()
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.list_img
        private val titleTV: TextView = view.title
        private val authorTV: TextView = view.author
        private val dateTV: TextView = view.date
        val bookmarkImg: ImageView = view.bookmark
        private val labelTV: TextView = view.labelTV

        fun bind(post: ItemsItem) {
            val url = post.images?.get(0)?.url ?: ""
            if (url.isNotEmpty()) {
                picasso.load(url).fit().into(image)
            }

            titleTV.text = post.title
            authorTV.text = post.author.displayName
            val date = DateUtils.getReadableDate(post.published)
            if (date.isNotEmpty()) {
                dateTV.text = date
            } else dateTV.gone()

            val labels = post.labels ?: emptyList()
            if (labels.isNotEmpty()) {
                labelTV.text = labels[0]
            }
        }
    }
}