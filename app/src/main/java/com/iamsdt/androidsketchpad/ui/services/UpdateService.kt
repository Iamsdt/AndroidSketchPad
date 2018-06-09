/*
 * Created by Shudipto Trafder
 * on 6/9/18 9:29 PM
 */

package com.iamsdt.androidsketchpad.ui.services

import android.arch.lifecycle.LifecycleService
import android.arch.lifecycle.Observer
import android.content.Intent
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import dagger.android.AndroidInjection
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class UpdateService : LifecycleService() {

    @Inject
    lateinit var remoteDataLayer: RemoteDataLayer

    @Inject
    lateinit var bus: EventBus

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("Update Service start")

        remoteDataLayer.getBlogDetails()
        remoteDataLayer.getPageDetails()
        remoteDataLayer.getPostDetailsForFirstTime()

        remoteDataLayer.getPostWithToken()

        remoteDataLayer.layerUtils
                .serviceLiveData.observe(this, Observer {
            // TODO: 6/9/2018 add logic
        })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }
}