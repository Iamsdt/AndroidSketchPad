/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:12 AM
 */

/*
 * Developed by Shudipto Trafder
 * at  5/26/18 6:29 PM.
 */

package com.iamsdt.androidsketchpad.utils


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasBroadcastReceiverInjector
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ConnectivityChangeReceiver
    : BroadcastReceiver(), HasBroadcastReceiverInjector {

    @Inject
    lateinit var dInjector: DispatchingAndroidInjector<BroadcastReceiver>

    @Inject
    lateinit var bus: EventBus

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = dInjector

    override fun onReceive(context: Context?, intent: Intent?) {

        AndroidInjection.inject(this,context)

        val status = getInternetStatus(context)

        //must check null
        //in aeroplane mode it's null
        if (status) {
            bus.post(EventMessage(ConstantUtils.internet,ConstantUtils.connected,1))
        } else{
            bus.post(EventMessage(ConstantUtils.internet,ConstantUtils.noInternet,0))
        }
    }

    companion object {
        fun getInternetStatus(context: Context?):Boolean{
            val manager = context?.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info:NetworkInfo ?= manager.activeNetworkInfo

            //must check null
            //in aeroplane mode it's null
            return info != null && info.isConnectedOrConnecting
        }
    }
}