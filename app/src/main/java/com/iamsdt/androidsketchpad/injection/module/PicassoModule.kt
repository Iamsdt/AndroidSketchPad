package com.iamsdt.androidsketchpad.injection.module

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestHandler
import dagger.Module
import okhttp3.OkHttpClient

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:06 PM
 */

@Module (includes = [ContextModule::class,NetworkModule::class])
class PicassoModule {

    fun getPicasso(context: Context,downloader: Downloader): Picasso =
            Picasso.Builder(context)
                    .downloader(downloader)
                    .loggingEnabled(true)
                    .build()

    fun getDownloader(client: OkHttpClient):Downloader =
            OkHttp3Downloader(client)

}