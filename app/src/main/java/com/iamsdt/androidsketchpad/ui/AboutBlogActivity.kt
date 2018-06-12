/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:20 PM
 */

package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.about_blog.*
import kotlinx.android.synthetic.main.activity_aboutblog.*
import javax.inject.Inject

class AboutBlogActivity:AppCompatActivity(){

    @Inject
    lateinit var spUtils: SpUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_aboutblog)
        setSupportActionBar(toolbar)

        val blog = spUtils.getBlog()

        //todo fix if blog empty

        toolbar.title = blog.name
        nameTv.text = blog.name
        desTV.text = blog.des

        val pub = "Live since ${DateUtils.getReadableDate(blog.published)}"
        publishedTv.text = pub

        val post = "Total post : ${blog.post}"
        postCount.text = post

        val page = "Total Page : ${blog.page}"
        pageCount.text = page

        val up = "Last update : ${blog.update}"
        updateTV.text = up

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}