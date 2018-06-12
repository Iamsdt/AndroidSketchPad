/*
 * Created by Shudipto Trafder
 * on 6/12/18 8:52 PM
 */

package com.iamsdt.androidsketchpad.ui.page

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.database.table.PageTable
import com.iamsdt.androidsketchpad.ui.details.DetailsActivity
import com.iamsdt.androidsketchpad.utils.DateUtils
import kotlinx.android.synthetic.main.page_list_item.view.*

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
        val model = list[position]
        holder.bind(model)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(Intent.EXTRA_HTML_TEXT, model.id)
            context.startActivity(intent)
        }
    }

    fun submitList(newList: List<PageTable>){
        list = newList

        //our list is just like constant
        notifyDataSetChanged()
    }

    inner class PageVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.titleTv
        val published: TextView = view.publishedTv
        val update: TextView = view.updateTv

        fun bind(page: PageTable) {
            title.text = page.title

            title.setTextColor(context.resources.getColor(R.color.cyan_500))

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
            // TODO: 6/12/2018 add color array
            return 0
        }
    }
}