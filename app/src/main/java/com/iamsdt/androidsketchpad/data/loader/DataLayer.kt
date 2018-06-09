/*
 * Created by Shudipto Trafder
 * on 6/9/18 9:27 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.SpUtils

class DataLayer(val remoteDataLayer: RemoteDataLayer,
                val postTableDao: PostTableDao,
                val pageTableDao: PageTableDao,
                val spUtils: SpUtils){



    fun getDataForFirstTime(): LiveData<PagedList<PostTable>> {

        val source = postTableDao.getAllPost

        val config = PagedList.Config.Builder()
                .setPageSize(7)
                .setInitialLoadSizeHint(10)//by default page size * 3
                .setPrefetchDistance(5) // default page size
                .setEnablePlaceholders(true) //default true
                // that's means scroll bar is not jump and all data set show on the
                //recycler view first after 30 it will show empty view
                // when load it will update with animation
                .build()

        val data = LivePagedListBuilder(source,config).build()

        val mediatorLiveData = MediatorLiveData<PagedList<PostTable>>()
        mediatorLiveData.addSource(data,{
            if (it == null || it.size <= 0){
                remoteDataLayer.getPostDetailsForFirstTime()
            } else if(isReadyForNextToken() && it.size >= 7){ //7 for initial size
                remoteDataLayer.getPostWithToken()
            }
        })

        return mediatorLiveData
    }

    private fun isReadyForNextToken():Boolean{
        val newToken = spUtils.getPageToken
        val usedPageToken = spUtils.getUsedPageToken

        if (usedPageToken.isNotEmpty() && newToken != usedPageToken){
            return true
        } else if (usedPageToken.isEmpty()){
            // no token is used yet
            return true
        }

        return false
    }
}