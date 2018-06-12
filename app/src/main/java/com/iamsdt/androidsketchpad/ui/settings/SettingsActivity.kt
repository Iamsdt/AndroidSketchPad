/*
 * Created by Shudipto Trafder
 * on 6/12/18 3:35 PM
 */

/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:19 PM
 */

package com.iamsdt.androidsketchpad.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.iamsdt.androidsketchpad.R
import com.iamsdt.themelibrary.ThemeUtils
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}