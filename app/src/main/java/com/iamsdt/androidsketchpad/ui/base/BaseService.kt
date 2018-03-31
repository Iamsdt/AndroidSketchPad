package com.iamsdt.androidsketchpad.ui.base

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.annotation.UiThread
import com.iamsdt.androidsketchpad.MyApplication
import com.iamsdt.androidsketchpad.injection.DaggerServiceComponent
import com.iamsdt.androidsketchpad.injection.ServiceComponent
import com.iamsdt.androidsketchpad.injection.module.ServiceModule

@SuppressLint("Registered")
/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:26 PM
 */
abstract class BaseService:Service(){

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): ServiceComponent =
            DaggerServiceComponent.builder()
                    .serviceModule(ServiceModule(this))
                    .applicationComponent(MyApplication().get(this).getComponent())
                    .build()

}