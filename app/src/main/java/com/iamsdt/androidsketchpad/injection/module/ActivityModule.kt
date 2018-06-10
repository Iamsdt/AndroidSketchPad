/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:05 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import com.iamsdt.androidsketchpad.ui.SplashScreen
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector
    abstract fun getMainActivity():SplashScreen

    @ContributesAndroidInjector
    abstract fun connectionReciver():ConnectivityChangeReceiver
}