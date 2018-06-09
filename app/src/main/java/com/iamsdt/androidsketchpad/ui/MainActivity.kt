package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.BuildConfig
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.utils.ext.blockingObserve
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun busEvent(eventMessage: EventMessage){

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
