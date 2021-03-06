/*
 * Created by Shudipto Trafder
 * on 6/12/18 8:52 PM
 */

package com.iamsdt.androidsketchpad.ui.page

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.table.PageTable
import com.iamsdt.androidsketchpad.utils.DateUtils
import kotlinx.android.synthetic.main.page_list_item.view.*
import java.util.*

class PageAdapter(val context: Context) : RecyclerView.Adapter<PageAdapter.PageVH>() {

    private var list: List<PageTable> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageVH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.page_list_item, parent, false)
        return PageVH(view)
    }

    override fun getItemCount(): Int =
            list.size

    override fun onBindViewHolder(holder: PageVH, position: Int) {
        val model:PageTable ?= list[position]
        if (model != null){
            holder.bind(model)
            holder.itemView.setOnClickListener {
                val intent = Intent(context, PageDetailsActivity::class.java)
                intent.putExtra(Intent.EXTRA_HTML_TEXT, model.id)
                context.startActivity(intent)
            }
        }
    }

    fun submitList(newList: List<PageTable>){

        list = newList

        AsyncListDiffer<PageTable>(this, object : DiffUtil.ItemCallback<PageTable>() {
            override fun areItemsTheSame(oldItem: PageTable?, newItem: PageTable?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: PageTable?, newItem: PageTable?): Boolean {
                return oldItem == newItem
            }

        }).submitList(list)
    }

    inner class PageVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.titleTv
        val published: TextView = view.publishedTv
        val update: TextView = view.updateTv

        fun bind(page: PageTable) {
            title.text = page.title
            getColor()

            val pub = "Live since ${DateUtils.getReadableDate(page.published)}"
            val up = "Last update : ${DateUtils.getReadableDate(page.updated)}"

            published.text = pub
            update.text = up
        }

        private fun getColor(){
            val color = ContextCompat.getColor(context,getRandomColor())
            title.setTextColor(color)
        }

        private fun getRandomColor():Int{
            // complete: 6/12/2018 add color array

            val array = listOf(R.color.cyan_500,
                    R.color.indigo_500,
                    R.color.orange_500,
                    R.color.purple_500,
                    R.color.deep_orange_500,
                    R.color.green_500,
                    R.color.pink_500,
                    R.color.light_blue_500,
                    R.color.light_green_500)

            val random= Random()
            val value =random.nextInt(8)

            return array[value]
        }
    }
}