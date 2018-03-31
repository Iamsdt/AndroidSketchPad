package com.iamsdt.androidsketchpad.ui.base

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder

@SuppressLint("Registered")
/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:26 PM
 */
class BaseService:Service(){
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}