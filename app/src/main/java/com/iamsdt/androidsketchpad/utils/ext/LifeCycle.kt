/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:15 AM
 */

/*
 * Developed by Shudipto Trafder
 * at  5/26/18 5:17 PM.
 */

package com.iamsdt.androidsketchpad.utils.ext

import android.app.Activity
import android.app.Application
import android.os.Bundle

open class LifeCycle : Application.ActivityLifecycleCallbacks {

     override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}