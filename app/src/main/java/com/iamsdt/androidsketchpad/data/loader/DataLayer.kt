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
import com.iamsdt.androidsketchpad.database.table.PageTable
import com.iamsdt.androidsketchpad.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.SpUtils
import kotlinx.coroutines.experimental.async
import timber.log.Timber

class DataLayer(private val remoteDataLayer: RemoteDataLayer,
                private val postTableDao: PostTableDao,
                private val pageTableDao: PageTableDao,
                private val spUtils: SpUtils) {


    fun getPostData(): LiveData<PagedList<PostTable>> {

        val source = postTableDao.getAllPost

        val newToken = spUtils.getPageToken
        val usedPageToken = spUtils.getUsedPageToken

        val config = PagedList.Config.Builder()
                .setPageSize(7)
                .setInitialLoadSizeHint(10)//by default page size * 3
                .setPrefetchDistance(5) // default page size
                .setEnablePlaceholders(true) //default true
                // that's means scroll bar is not jump and all data set show on the
                //recycler view first after 30 it will show empty view
                // when load it will update with animation
                .build()

        val data = LivePagedListBuilder(source, config).build()

        val mediatorLiveData = MediatorLiveData<PagedList<PostTable>>()
        mediatorLiveData.addSource(data, {
            if (it == null || it.size <= 0) {
                Timber.i("Post List is null or empty so call for remote data")
                remoteDataLayer.getPostDetailsForFirstTime()
            } else if (isReadyForNextToken(newToken, usedPageToken) && it.size >= 7) { //7 for initial size
                Timber.i("Post List is greater than 7 so call for next page from token")
                remoteDataLayer.getPostWithToken(newToken)
            }
        })

        return mediatorLiveData
    }

    fun getPageData(): LiveData<PageTable> {
        val data = pageTableDao.getAllPage
        val mediatorLiveData = MediatorLiveData<PageTable>()

        mediatorLiveData.addSource(data, {
            if (it == null) {
                Timber.i("Page table is null so call remote data")
                remoteDataLayer.getPageDetails()
            }
        })

        return mediatorLiveData
    }

    private fun isReadyForNextToken(newToken: String, usedPageToken: String): Boolean {

        Timber.i("Compare token:$newToken and oldToken$usedPageToken")

        if (usedPageToken.isNotEmpty() && newToken != usedPageToken) {
            return true
        } else if (usedPageToken.isEmpty() && newToken.isNotEmpty()) {
            // no token is used yet
            return true
        }

        return false
    }
}