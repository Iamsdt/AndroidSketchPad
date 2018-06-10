/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:21 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import com.iamsdt.androidsketchpad.BuildConfig
import com.iamsdt.androidsketchpad.data.loader.DataLayer
import com.iamsdt.androidsketchpad.data.loader.LayerUtils
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.ui.main.MainAdapter
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DBModule::class,PicassoModule::class])
class AppModule {


    @Provides
    @Singleton
    fun getAdapter(picasso: Picasso,
                   spUtils: SpUtils,
                   postTableDao: PostTableDao,
                   application: Application):MainAdapter =
            MainAdapter(picasso,
                    spUtils.getAuthor().displayName,
                    postTableDao,
                    application)

    @Provides
    @Singleton
    fun getDataLayer(remoteDataLayer: RemoteDataLayer,
                     postTableDao: PostTableDao,
                     pageTableDao: PageTableDao,
                     spUtils: SpUtils): DataLayer =
            DataLayer(remoteDataLayer, postTableDao, pageTableDao, spUtils)


    @Provides
    @Singleton
    fun getRemoteDataLayer(spUtils: SpUtils,
                           retInterface: RetInterface,
                           layerUtils: LayerUtils): RemoteDataLayer =
            RemoteDataLayer(spUtils, retInterface, layerUtils, BuildConfig.BloggerApiKey)


    @Provides
    @Singleton
    fun getLayerUtils(spUtils: SpUtils,
                      pageTableDao: PageTableDao,
                      postTableDao: PostTableDao): LayerUtils =
            LayerUtils(spUtils, pageTableDao, postTableDao)

    @Provides
    @Singleton
    fun getSp(application: Application): SpUtils = SpUtils(application)
}