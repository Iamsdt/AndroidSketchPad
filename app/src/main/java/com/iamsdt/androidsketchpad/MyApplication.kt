package com.iamsdt.androidsketchpad

import android.app.Application
import com.iamsdt.androidsketchpad.injection.ApplicationComponent
import com.iamsdt.androidsketchpad.injection.DaggerApplicationComponent
import com.iamsdt.androidsketchpad.injection.module.ContextModule
import com.iamsdt.androidsketchpad.ui.base.BaseActivity
import com.iamsdt.androidsketchpad.ui.base.BaseFragment
import com.iamsdt.androidsketchpad.ui.base.BaseService
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 10:59 AM
 */
class MyApplication : Application() {

    private var dagger: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.asTree())
        }

        dagger = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this.baseContext))
                .build()

    }

    fun getComponent() = dagger

    fun get(activity: BaseActivity): MyApplication
            = activity.application as MyApplication

    fun get(fragment: BaseFragment): MyApplication
            = fragment.activity?.application as MyApplication

    fun get(context:BaseService):MyApplication =
            context.applicationContext as MyApplication

}