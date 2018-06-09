/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:14 AM
 */

package com.iamsdt.androidsketchpad.utils.ext

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass


fun AppCompatActivity.getThread(timer: Long, clazz: KClass<out AppCompatActivity>) =
        Thread({
            try {
                Thread.sleep(timer)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                toNextActivity(clazz)
                finish()
            }
        })

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
