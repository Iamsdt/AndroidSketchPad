/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:15 AM
 */

/*
 * Developed by Shudipto Trafder
 * at  5/28/18 11:28 PM.
 */

package com.iamsdt.androidsketchpad.utils.ext

import android.os.Build
import android.util.Log
import timber.log.Timber

class DebugLogTree:Timber.DebugTree(){

    override fun createStackElementTag(element: StackTraceElement): String? {
        //super.createStackElementTag(element)
        return "C:${element.className.javaClass.simpleName} - M:${element.methodName} -" +
                " LN:${element.lineNumber}"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        var p = priority

        if (Build.MANUFACTURER == "HUAWEI" || Build.MANUFACTURER == "samsung") {
            if (p == Log.VERBOSE || p == Log.DEBUG || p == Log.INFO)
                p = Log.ERROR
        }

        super.log(p, tag, message, t)
    }
}