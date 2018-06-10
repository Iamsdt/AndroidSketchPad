/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:14 AM
 */

package com.iamsdt.androidsketchpad.utils.ext

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlin.reflect.KClass


fun AppCompatActivity.runThread(timer: Long, clazz: KClass<out AppCompatActivity>) =
        Thread({
            try {
                Thread.sleep(timer)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                toNextActivity(clazz)
                finish()
            }
        }).start()

fun AppCompatActivity.toNextActivity(
        clazz: KClass<out AppCompatActivity>,
        extraKey: String = "",
        extra: String = "",
        finish:Boolean = false) {
    val intent = Intent(this, clazz.java)

    if (extraKey.isNotEmpty()) {
        intent.putExtra(extraKey,extra)
    }

    startActivity(intent)

    if (finish){
        finish()
    }
}

fun AppCompatActivity.showToast(
        type: ToastType,
        message: String,
        time:Int = Toast.LENGTH_SHORT,
        withIcon:Boolean = true){

    when (type) {
        ToastType.INFO -> Toasty.info(this,message,time,withIcon).show()
        ToastType.ERROR -> Toasty.error(this,message,time,withIcon).show()
        ToastType.SUCCESSFUL -> Toasty.success(this,message,time,withIcon).show()
        ToastType.WARNING -> Toasty.warning(this,message,time,withIcon).show()
    }
}



