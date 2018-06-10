package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.connected
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.internet
import com.iamsdt.androidsketchpad.utils.ext.ToastType
import com.iamsdt.androidsketchpad.utils.ext.showToast
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var bus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun busEvent(eventMessage: EventMessage){
        if (eventMessage.key == internet){
            if (eventMessage.message == connected){
                showToast(ToastType.SUCCESSFUL,"Network connected")
            } else{
                showToast(ToastType.WARNING,"No Internet")
            }
        }
    }


    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        bus.unregister(this)
    }
}
