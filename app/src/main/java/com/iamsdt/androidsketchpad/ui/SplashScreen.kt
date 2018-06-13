package com.iamsdt.androidsketchpad.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.main.MainActivity
import com.iamsdt.androidsketchpad.ui.services.UpdateService
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.DateUtils
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.runThread
import com.iamsdt.androidsketchpad.utils.ext.toNextActivity
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val set = AnimationUtils.loadAnimation(this,
                R.anim.splash_screen_animation)

        app_icon.animation = set
        app_name.animation = set

        set.start()

        if (spUtils.isFirstTime) {

            //start service
            //startService(Intent(this, UpdateService::class.java))

            //show app intro
            //runThread(1000,AppIntro::class)

            //spUtils.setAppRunFirstTime()
            toNextActivity(MainActivity::class)
        } else {
            //show main screen
            runThread(1500,MainActivity::class)
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
