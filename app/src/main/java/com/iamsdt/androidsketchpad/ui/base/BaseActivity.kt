package com.iamsdt.androidsketchpad.ui.base

import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.MyApplication
import com.iamsdt.androidsketchpad.injection.ActivityComponent
import com.iamsdt.androidsketchpad.injection.DaggerActivityComponent
import com.iamsdt.androidsketchpad.injection.module.ActivityModule

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:08 PM
 */
abstract class BaseActivity:AppCompatActivity(){

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): ActivityComponent =
            DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .applicationComponent(MyApplication().get(this).getComponent())
                    .build()

}