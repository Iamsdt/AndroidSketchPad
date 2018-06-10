package com.iamsdt.androidsketchpad.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.loader.DataLayer
import com.iamsdt.androidsketchpad.ui.main.MainActivity
import com.iamsdt.androidsketchpad.ui.services.UpdateService
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.connected
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.internet
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.SERVICE
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.androidsketchpad.utils.ext.toNextActivity
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var spUtils: SpUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (spUtils.isFirstTime){
            //show app intro
            //runThread(500,)
            toNextActivity(MainActivity::class)
        } else{
            //show main screen
            //runThread(1000,)
        }


        //runServices()
    }

    private fun runServices(){
        if (ConnectivityChangeReceiver.getInternetStatus(this)
                && timeForUpdate()){
            startService(Intent(this,UpdateService::class.java))
        }
    }

    private fun timeForUpdate():Boolean{
        return false
    }
}
