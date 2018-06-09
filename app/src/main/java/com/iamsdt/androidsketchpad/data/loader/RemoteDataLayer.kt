/*
 * Created by Shudipto Trafder
 * on 6/9/18 6:20 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import timber.log.Timber

class RemoteDataLayer(private val retInterface: RetInterface,
                      val layerUtils: LayerUtils,
                      private val apiKey: String) {


    fun getBlogDetails() {
        Timber.i("Request for blog data")
        val call = retInterface.getBlog()
        layerUtils.executeBlogCall(call)
    }

    fun getPostDetailsForFirstTime() {
        Timber.i("Request for post data for first time")
        val call = retInterface.getPostForFirstTime(apiKey)
        layerUtils.executePostCall(call)
    }

    fun getPostWithToken(token: String) {
        Timber.i("Request for post data with token:$token")
        if (token.isEmpty() && !isAlreadyRequested) {
            Timber.i("Executing post Request data with token:$token")
            val call = retInterface.getPostWithToken(token, apiKey)
            layerUtils.executePostCall(call, token)
            isAlreadyRequested = true
        }

    }

    fun getPostFromSearch(searchEvent: String) {
        Timber.i("Request post from search:$searchEvent")
        val call = retInterface.getPostFormSearch(searchEvent, apiKey)
        layerUtils.executePostCall(call)
    }

    fun getPageDetails() {
        Timber.i("Request for page details")
        val call = retInterface.getPageList(apiKey)
        layerUtils.executePageCall(call)
    }

    companion object {
        var isAlreadyRequested = false
    }
}