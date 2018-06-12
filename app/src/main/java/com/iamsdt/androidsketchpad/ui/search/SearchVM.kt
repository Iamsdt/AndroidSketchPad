/*
 * Created by Shudipto Trafder
 * on 6/12/18 10:32 PM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.arch.lifecycle.ViewModel
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import javax.inject.Inject

class SearchVM @Inject constructor(val remoteDataLayer: RemoteDataLayer):ViewModel(){


    fun requestSearch(document:String){
        remoteDataLayer.getPostFromSearch(document)
    }

    val searchData = remoteDataLayer.layerUtils.searchData
    val searchEvent = remoteDataLayer.layerUtils.searchEvent

}