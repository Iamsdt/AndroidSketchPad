/*
 * Created by Shudipto Trafder
 * on 7/1/18 5:47 PM
 */

package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class PicassoModule{
    @Provides
    @Singleton
    fun getPicasso(context: Application, client: OkHttpClient): Picasso
            = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(client))
            .build()
}