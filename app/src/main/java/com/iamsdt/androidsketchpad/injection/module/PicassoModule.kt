package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:06 PM
 */

@Module
class PicassoModule {

    @Provides
    @Singleton
    fun getPicasso(context: Application,downloader: Downloader): Picasso =
            Picasso.Builder(context)
                    .downloader(downloader)
                    .loggingEnabled(true)
                    .build()

    @Provides
    @Singleton
    fun getDownloader(client: OkHttpClient):Downloader =
            OkHttp3Downloader(client)

}