package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        //dagger inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
