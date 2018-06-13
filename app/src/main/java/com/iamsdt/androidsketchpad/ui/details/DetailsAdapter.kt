/*
 * Created by Shudipto Trafder
 * on 6/13/18 9:21 PM
 */

package com.iamsdt.androidsketchpad.ui.details

import android.app.Application
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
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.ext.changeHeight
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.iamsdt.androidsketchpad.utils.ext.inVisible
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.main_list_item.view.*
import timber.log.Timber

class DetailsAdapter(private val picasso: Picasso,
                     author: String,
                     private val postTableDao: PostTableDao,
                     context: Application)
    : RecyclerView.Adapter<DetailsAdapter.ItemViewHolder>() {

    val author = "By $author"

    private var context: Context = context.baseContext

    private var list: List<PostTable> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return ItemViewHolder(view)
    }

    //must change context to avoid crash
    fun changeContext(context: Context) {
        this.context = context
        Timber.i("Change context to activity context")
    }

    fun submitList(newList: List<PostTable>) {
        list = newList
        //list will be upto 3
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
            list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val model = list[position]

        holder.bind(model, picasso, context, author)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, model.id)
            context.startActivity(intent)
        }

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
                        //holder.bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_done))
                    }

                    if (delete > 0) {
                        Toasty.warning(context, "Bookmark deleted", Toast.LENGTH_SHORT, true).show()
                        //holder.bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark))
                    }
                })

                thread.quitSafely()
            })
        }

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.list_img
        private val titleTV: TextView = view.title
        private val authorTV: TextView = view.author
        private val dateTV: TextView = view.date
        val bookmarkImg: ImageView = view.bookmark
        private val labelTV: TextView = view.labelTV

        fun bind(post: PostTable?, picasso: Picasso,
                 context: Context, author: String) {
            val url = post?.imgUrl?.get(0)?.url ?: ""
            if (url.isNotEmpty()) {
                picasso.load(url).fit().into(image)
            } else {
                image.inVisible()
                image.changeHeight(72)
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
            } else {
                bookmarkImg.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark))
            }
        }
    }
}