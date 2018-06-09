/*
 * Created by Shudipto Trafder
 * on 6/9/18 6:20 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.utils.SpUtils

class RemoteDataLayer(private val spUtils: SpUtils,
                      private val retInterface: RetInterface,
                      val layerUtils: LayerUtils,
                      private val apiKey: String) {


    fun getBlogDetails() {
        val call = retInterface.getBlog()
        layerUtils.executeBlogCall(call)
    }

    fun getPostDetailsForFirstTime() {
        val call = retInterface.getPostForFirstTime(apiKey)
        layerUtils.executePostCall(call)
    }

    fun getPostWithToken() {
        val token = spUtils.getPageToken
        if (token.isEmpty()) {
            val call = retInterface.getPostWithToken(token, apiKey)
            layerUtils.executePostCall(call)
        }

    }

    fun getPostFromSearch(searchEvent: String) {
        val call = retInterface.getPostFormSearch(searchEvent, apiKey)
        layerUtils.executePostCall(call)
    }

    fun getPageDetails() {
        val call = retInterface.getPageList(apiKey)
        layerUtils.executePageCall(call)
    }
}