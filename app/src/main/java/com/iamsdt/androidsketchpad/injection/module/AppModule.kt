/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:21 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import com.iamsdt.androidsketchpad.BuildConfig
import com.iamsdt.androidsketchpad.data.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.loader.LayerUtils
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.ui.bookmark.BookmarkAdapter
import com.iamsdt.androidsketchpad.ui.main.MainAdapter
import com.iamsdt.androidsketchpad.ui.search.SearchAdapter
import com.iamsdt.androidsketchpad.utils.SpUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DBModule::class])
class AppModule {

    @Provides
    @Singleton
    fun getSearchAdapter(
            postTableDao: PostTableDao,
            application: Application): SearchAdapter =
            SearchAdapter(postTableDao,
                    application)

    @Provides
    @Singleton
    fun getBookmarkAdapter(spUtils: SpUtils,
                           postTableDao: PostTableDao,
                           application: Application): BookmarkAdapter =
            BookmarkAdapter(spUtils.getAuthor().displayName,
                    postTableDao,
                    application)

    @Provides
    @Singleton
    fun getAdapter(spUtils: SpUtils,
                   postTableDao: PostTableDao,
                   application: Application): MainAdapter =
            MainAdapter(spUtils.getAuthor().displayName,
                    postTableDao,
                    application)

    @Provides
    @Singleton
    fun getRemoteDataLayer(spUtils: SpUtils,
                           retInterface: RetInterface,
                           layerUtils: LayerUtils): RemoteDataLayer =
            RemoteDataLayer(retInterface, layerUtils, BuildConfig.BloggerApiKey, spUtils)


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