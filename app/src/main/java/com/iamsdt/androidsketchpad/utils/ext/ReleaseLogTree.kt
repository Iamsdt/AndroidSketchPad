/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:16 AM
 */


package com.iamsdt.androidsketchpad.utils.ext

import android.util.Log
import timber.log.Timber


class ReleaseLogTree : Timber.Tree() {

    private val maxLogLength = 4000

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        // Don't log VERBOSE, DEBUG and INFO
        return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
        // Log only ERROR, WARN and WTF
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // complete: 5/28/2018 set a crash library
//        if (priority == Log.ERROR || priority == Log.WARN)
//            CrashLibrary.log(priority, tag, message)

        if (isLoggable(tag, priority)) {
            //show on the console
            if (message.length < maxLogLength) {
                // Message is short enough, doesn't need to be broken into chunks
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
            } else {
                // Split by line, then ensure each line can fit into Log's max length
                var i = 0
                val length = message.length
                while (i < length) {
                    var newline = message.indexOf('\n', i)
                    newline = if (newline != -1) newline else length
                    do {
                        val end = Math.min(newline, i + maxLogLength)
                        val part = message.substring(i, end)
                        if (priority == Log.ASSERT) {
                            Log.wtf(tag, part)
                        } else {
                            Log.println(priority, tag, part)
                        }
                        i = end
                    } while (i < newline)
                    i++
                }
            }
        }
    }
}