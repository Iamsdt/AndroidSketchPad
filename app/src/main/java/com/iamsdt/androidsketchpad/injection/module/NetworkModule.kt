package com.iamsdt.androidsketchpad.injection.module

import android.content.Context
import com.iamsdt.androidsketchpad.injection.scopes.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:53 AM
 */

@Module (includes = [ContextModule::class])
class NetworkModule{

    @Provides
    @AppScope
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
    @AppScope
    fun getCache(file: File): Cache = Cache(file, 10 * 1024 * 1024) //10mb

    @Provides
    @AppScope
    fun getFile(context: Context): File = File(context.cacheDir, "okHttp")

}