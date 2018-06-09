package com.iamsdt.androidsketchpad.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 12:00 PM
 */

@Module (includes = [NetworkModule::class])
class RetrofitModule {

    @Provides
    @Singleton
    fun getWPRestInterface(@Named("post") retrofit: Retrofit): RetInterface =
            retrofit.create(RetInterface::class.java)

    @Provides
    @Singleton
    @Named("detailsRest")
    fun getWPRestInterfaceDetails(@Named("details") retrofit: Retrofit): RetInterface =
            retrofit.create(RetInterface::class.java)

    @Provides
    @Singleton
    @Named("post")
    fun getRetrofitPost(okHttpClient: OkHttpClient, gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/blogger/v3/blogs/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    @Named("details")
    fun getRetrofitDetails(okHttpClient: OkHttpClient,gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/blogger/v3/blogs/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @Singleton
    fun getGson():Gson = GsonBuilder().create()
}