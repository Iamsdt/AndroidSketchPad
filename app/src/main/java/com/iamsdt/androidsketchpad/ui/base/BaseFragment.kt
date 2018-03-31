package com.iamsdt.androidsketchpad.ui.base

import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import com.iamsdt.androidsketchpad.MyApplication
import com.iamsdt.androidsketchpad.injection.DaggerFragmentComponent
import com.iamsdt.androidsketchpad.injection.FragmentComponent
import com.iamsdt.androidsketchpad.injection.module.FragmentModule

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:25 PM
 */
abstract class BaseFragment:Fragment(){

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): FragmentComponent =
            DaggerFragmentComponent.builder()
                    .fragmentModule(FragmentModule(this))
                    .applicationComponent(MyApplication().get(this).getComponent())
                    .build()

}