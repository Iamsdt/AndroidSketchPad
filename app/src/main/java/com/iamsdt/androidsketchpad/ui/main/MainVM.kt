/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:18 AM
 */

package com.iamsdt.androidsketchpad.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.utils.ConstantUtils
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.SingleLiveEvent
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import timber.log.Timber
import javax.inject.Inject

class MainVM @Inject constructor(val remoteDataLayer: RemoteDataLayer,
                                 val postTableDao: PostTableDao,
                                 val spUtils: SpUtils) : ViewModel() {

    private var initialSize = 15

    fun getPostData(): LiveData<PagedList<PostTable>> {

        val source = postTableDao.getAllPost

        val config = PagedList.Config.Builder()
                .setPageSize(5)
                .setInitialLoadSizeHint(9)//by default page size * 3
                .setPrefetchDistance(4) // default page size
                .setEnablePlaceholders(true) //default true
                // that's means scroll bar is not jump and all data set show on the
                //recycler view first after 30 it will show empty view
                // when load it will update with animation
                .build()

        //        val mediatorLiveData = MediatorLiveData<PagedList<PostTable>>()
//        mediatorLiveData.addSource(data, {
//            if (it == null || it.size <= 0) {
//                Timber.i("Post List is null or empty so call for remote data")
//                remoteDataLayer.getPostDetailsForFirstTime()
//            } else{
//
//            }
//        })

        return LivePagedListBuilder(source, config).build()
    }

    val uiLiveData: SingleLiveEvent<EventMessage> = remoteDataLayer.layerUtils.uiLIveEvent

    fun nextPost() {
        val token = spUtils.getPageToken
        Timber.i("Token :$token")
        //if token is empty then first call is not finished successfully
        if (token.isNotEmpty())
            remoteDataLayer.getPostWithToken(token, false)
        else
            remoteDataLayer.getPostDetailsForFirstTime(false)
    }

    fun requestNewPost(int: Int) {

        val result = int - initialSize

        Timber.i("VM result:$result")

        if (result > 4) {
            uiLiveData.postValue(EventMessage(ConstantUtils.Event.POST_KEY, "request", 0))
            initialSize = int
        }
    }
}