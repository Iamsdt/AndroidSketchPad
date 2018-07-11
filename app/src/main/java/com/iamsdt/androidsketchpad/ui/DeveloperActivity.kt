/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:20 PM
 */

package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.analytics.FirebaseAnalytics
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.ext.customTab
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_developer.*
import kotlinx.android.synthetic.main.developer.*

class DeveloperActivity: AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_developer)
        setSupportActionBar(toolbar)

        //Search
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Developer")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Developer Activity")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Developer Details")
        FirebaseAnalytics.getInstance(this)
                .logEvent(FirebaseAnalytics.Event.GENERATE_LEAD,bundle)

        facebook.setOnClickListener {
            customTab("https://facebook.com/iamsdt")
        }

        twitter.setOnClickListener {
            customTab("https://twitter.com/iamsdt")
        }


        github.setOnClickListener {
            customTab("https://github.com/Iamsdt")
        }

        linkedin.setOnClickListener {
            customTab("https://www.linkedin.com/in/iamsdt")
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}

