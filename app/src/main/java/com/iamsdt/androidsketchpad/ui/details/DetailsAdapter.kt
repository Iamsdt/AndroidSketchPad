/*
 * Created by Shudipto Trafder
 * on 6/13/18 9:21 PM
 */

package com.iamsdt.androidsketchpad.ui.details

import android.app.Activity
import android.content.Intent
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.ext.gone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_list_item.view.*
import timber.log.Timber

class DetailsAdapter(private val picasso: Picasso,
                     author: String,val context: Activity)
    : RecyclerView.Adapter<DetailsAdapter.ItemViewHolder>() {

    val author = "By $author"

    private var list: List<PostTable> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return ItemViewHolder(view)
    }


    fun submitList(newList: List<PostTable>) {
        //list will be upto 3
        if (list == newList) return

        list = newList
        //notifyDataSetChanged()

        AsyncListDiffer<PostTable>(this, object : DiffUtil.ItemCallback<PostTable>() {
            override fun areItemsTheSame(oldItem: PostTable?, newItem: PostTable?): Boolean {
                return oldItem?.id == newItem?.id && oldItem?.bookmark == newItem?.bookmark
            }

            override fun areContentsTheSame(oldItem: PostTable?, newItem: PostTable?): Boolean {
                return oldItem == newItem
            }

        }).submitList(list)

    }

    override fun getItemCount(): Int =
            list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        try {
            val model: PostTable? = list[position]

            if (model != null) {
                holder.bind(model)

                holder.itemView.setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra(Intent.EXTRA_TEXT, model.id)
                    context.startActivity(intent)
                    context.finish()
                }
            }
        } catch (e: Exception) {
            Timber.i(e)
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.list_img
        private val titleTV: TextView = view.title
        private val authorTV: TextView = view.author
        private val dateTV: TextView = view.date
        private val bookmarkImg: ImageView = view.bookmark
        private val labelTV: TextView = view.labelTV

        fun bind(post: PostTable) {

            val imageList = post.imgUrl ?: emptyList()

            if (imageList.isNotEmpty()) {
                picasso.load(imageList[0].url).fit().into(image)
            }


            titleTV.text = post.title
            authorTV.text = author
            val date = DateUtils.getReadableDate(post.published)
            if (date.isNotEmpty()) {
                dateTV.text = date
            } else dateTV.gone()

            val labels = post.labels ?: emptyList()
            if (labels.isNotEmpty()) {
                labelTV.text = labels[0]
            }

            bookmarkImg.gone()
        }
    }
}