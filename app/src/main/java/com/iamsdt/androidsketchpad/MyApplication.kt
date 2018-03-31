package com.iamsdt.androidsketchpad

import android.app.Application
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 10:59 AM
 */
class MyApplication:Application(){

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else{
            Timber.plant(Timber.asTree())
        }
    }
}