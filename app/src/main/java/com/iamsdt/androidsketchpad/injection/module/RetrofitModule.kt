package com.iamsdt.androidsketchpad.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.injection.scopes.AppScope
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 12:00 PM
 */

@Module
class RetrofitModule {
    @Provides
    @AppScope
    fun getWPRestInterface(@Named("post") retrofit: Retrofit): RetInterface =
            retrofit.create(RetInterface::class.java)

    @Provides
    @AppScope
    @Named("detailsRest")
    fun getWPRestInterfaceDetails(@Named("details") retrofit: Retrofit): RetInterface =
            retrofit.create(RetInterface::class.java)

    @Provides
    @AppScope
    @Named("post")
    fun getRetrofitPost(okHttpClient: OkHttpClient, gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl(ConstantUtils.retrofit.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @AppScope
    @Named("details")
    fun getRetrofitDetails(okHttpClient: OkHttpClient,gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl(ConstantUtils.retrofit.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @AppScope
    fun getGson():Gson = GsonBuilder().create()
}