/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:21 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import com.iamsdt.androidsketchpad.BuildConfig
import com.iamsdt.androidsketchpad.data.loader.LayerUtils
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.utils.SpUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun getDataLayer(spUtils: SpUtils,
                     retInterface: RetInterface,
                     layerUtils: LayerUtils):RemoteDataLayer =
            RemoteDataLayer(spUtils,retInterface,layerUtils,BuildConfig.BloggerApiKey)


    @Provides
    @Singleton
    fun getLayerUtils(spUtils: SpUtils,
                      pageTableDao: PageTableDao,
                      postTableDao: PostTableDao): LayerUtils =
            LayerUtils(spUtils,pageTableDao,postTableDao)

    @Provides
    @Singleton
    fun getSp(application: Application): SpUtils = SpUtils(application)
}