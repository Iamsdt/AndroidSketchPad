/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:20 PM
 */

package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.ext.customTab
import com.iamsdt.androidsketchpad.utils.ext.toNextActivity
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.about_app.*
import kotlinx.android.synthetic.main.activity_about_app.*

class AboutApp:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_about_app)
        setSupportActionBar(toolbar)

        app_developer.setOnClickListener {
            toNextActivity(DeveloperActivity::class)
        }

        app_license.setOnClickListener {
            //open license in custom tab
        }

        app_git.setOnClickListener {
            customTab("https://github.com/iamsdt")
        }

        mainBlog.setOnClickListener {
            //open license in custom tab
            customTab("https://androsketchpad.blogspot.com")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}