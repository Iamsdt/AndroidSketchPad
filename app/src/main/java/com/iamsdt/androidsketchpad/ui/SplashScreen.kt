package com.iamsdt.androidsketchpad.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.main.MainActivity
import com.iamsdt.androidsketchpad.ui.services.UpdateService
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.runThread
import com.iamsdt.androidsketchpad.utils.ext.toNextActivity
import timber.log.Timber
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (spUtils.isFirstTime) {

            //start service
            //startService(Intent(this, UpdateService::class.java))

            //show app intro
            //runThread(500,)
            //runThread(100,AppIntro::class)

            //spUtils.setAppRunFirstTime()
            //complete:6/10/2018 Debug only remove latter
            toNextActivity(AppIntro::class)
        } else {
            //show main screen
            //runThread(1000,)
            runThread(1000,MainActivity::class)
            runServices()
        }
    }

    private fun runServices() {
        if (ConnectivityChangeReceiver.getInternetStatus(this)
                && timeForUpdate()) {
            startService(Intent(this, UpdateService::class.java))
            Timber.i("Time for update, start service from splash screen")
        }
    }

    //this will run every day once
    private fun timeForUpdate(): Boolean {
        val blogUpdate = spUtils.getBlogUpdate

        if (blogUpdate == 0L) return false

        val interval = DateUtils.compareDateIntervals(blogUpdate)
        Timber.i("Blog update interval$interval")
        return interval >= 1
    }
}
