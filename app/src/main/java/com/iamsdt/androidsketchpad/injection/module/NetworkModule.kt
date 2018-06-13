package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:53 AM
 */

@Module
class NetworkModule{

    @Provides
    @Singleton
    fun getClient(cache: Cache): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES).build()
    }

    @Provides
    @Singleton
    fun getCache(file: File): Cache = Cache(file, 10 * 1024 * 1024) //10mb

    @Provides
    @Singleton
    fun getFile(context: Application): File = File(context.cacheDir, "okHttp")

}