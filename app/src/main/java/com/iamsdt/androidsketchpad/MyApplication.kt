package com.iamsdt.androidsketchpad

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.iamsdt.androidsketchpad.injection.AppComponent
import com.iamsdt.androidsketchpad.injection.DaggerAppComponent
import com.iamsdt.androidsketchpad.utils.ext.DebugLogTree
import com.iamsdt.androidsketchpad.utils.ext.LifeCycle
import com.iamsdt.androidsketchpad.utils.ext.ReleaseLogTree
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 10:59 AM
 */
class MyApplication : DaggerApplication() {

    val component:AppComponent by lazy {
        DaggerAppComponent.builder()
                .application(this)
                .build()
    }

    override fun applicationInjector():
            AndroidInjector<out DaggerApplication> = component


    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        if (BuildConfig.DEBUG) Timber.plant(DebugLogTree())
        else Timber.plant(ReleaseLogTree())

        registerActivityLifecycleCallbacks(object : LifeCycle() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
                activity?.let {
                    //is their any option to check
                    // is ContributesAndroidInjector available for this activity first
                    try {
                        AndroidInjection.inject(it)
                    }catch (e:Exception){
                        //Timber.d(e,"Inject error")
                    }
                }
            }
        })
    }
}