package com.iamsdt.androidsketchpad.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 12:00 PM
 */

@Module(includes = [NetworkModule::class])
class RetrofitModule {


    @Provides
    @Singleton
    fun getRestInterface(retrofit: Retrofit): RetInterface =
            retrofit.create(RetInterface::class.java)

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @Singleton
    fun getGson():Gson = GsonBuilder().create()
}