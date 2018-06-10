/*
 * Created by Shudipto Trafder
 * on 6/9/18 9:29 PM
 */

package com.iamsdt.androidsketchpad.ui.services

import android.arch.lifecycle.LifecycleService
import android.arch.lifecycle.Observer
import android.content.Intent
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.BLOG_KEY
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.PAGE_KEY
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.POST_KEY
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import dagger.android.AndroidInjection
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class UpdateService : LifecycleService() {

    @Inject
    lateinit var remoteDataLayer: RemoteDataLayer

    @Inject
    lateinit var bus: EventBus

    @Inject
    lateinit var spUtils: SpUtils

    private var postFinished = false
    private var pageFinished = false
    private var blogFinished = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("Update Service start")

        if (spUtils.isServiceFirstTime) {
            remoteDataLayer.getPostDetailsForFirstTime()
            remoteDataLayer.getPageDetails()
            remoteDataLayer.getBlogDetails()

        } else {
            remoteDataLayer.getBlogDetails()
            if (timeForUpdate()) {
                remoteDataLayer.getPostDetailsForFirstTime()
                remoteDataLayer.getPageDetails()
            }
        }

        remoteDataLayer.layerUtils
                .serviceLiveData.observe(this, Observer {
            if (it != null) {
                when (it.key) {
                    POST_KEY -> {
                        if (it.status == 1) {
                            postFinished = true
                            if (remoteDataLayer.isReadyForNextToken()) {
                                remoteDataLayer.getPostWithToken(spUtils.getPageToken)
                            }
                        } else {
                            if (it.message.isEmpty()){
                                if (isNet()) remoteDataLayer.getPostDetailsForFirstTime()
                            } else{
                                if (isNet()) remoteDataLayer.getPostWithToken(spUtils.getPageToken)
                            }
                        }
                    }
                    PAGE_KEY -> {
                        if (it.status == 1) {
                            pageFinished = true
                        } else {
                            if (isNet()) remoteDataLayer.getPageDetails()
                        }
                    }
                    BLOG_KEY -> {
                        if (it.status == 1) {
                            blogFinished = true
                            checkForOtherUpdate()
                        } else {
                            if (isNet()) remoteDataLayer.getBlogDetails()
                        }
                    }
                }

                setUpdateTime()
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }

    private fun setUpdateTime() {
        if (blogFinished && pageFinished && postFinished) {
            spUtils.setServiceRunDate()
            bus.post(EventMessage())
        }
    }

    private fun timeForUpdate(): Boolean {
        val date = spUtils.getServiceRunDate
        if (date == 0L) return true
        val interval = DateUtils.compareDateIntervals(date)
        val savedInterVal = spUtils.syncTimeInterval(this)

        return interval >= savedInterVal
    }


    private fun isNet() = ConnectivityChangeReceiver.getInternetStatus(this)

    private fun checkForOtherUpdate() {
        if (!timeForUpdate()) {
            postFinished = true
            blogFinished = true
        }
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        isRunningService = true
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunningService = false
    }

    companion object {
        var isRunningService = false
    }
}