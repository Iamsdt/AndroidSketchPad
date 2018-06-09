/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:21 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import com.iamsdt.androidsketchpad.utils.SpUtils
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Module
class AppModule{

    @Provides
    @Singleton
    fun getSp(application: Application):SpUtils
     = SpUtils(application)
}