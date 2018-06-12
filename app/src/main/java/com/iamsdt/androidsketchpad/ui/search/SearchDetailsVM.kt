/*
 * Created by Shudipto Trafder
 * on 6/12/18 10:33 PM
 */

package com.iamsdt.androidsketchpad.ui.search

import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import com.iamsdt.androidsketchpad.data.retrofit.model.singlePost.SinglePostResponse
import com.iamsdt.androidsketchpad.utils.ext.BookMark
import com.iamsdt.androidsketchpad.utils.ext.SingleLiveEvent
import javax.inject.Inject

class SearchDetailsVM @Inject constructor(
        val remoteDataLayer: RemoteDataLayer,
        val postTableDao: PostTableDao) : ViewModel() {


    fun getData(id: String) {
        remoteDataLayer.getSinglePost(id)
    }

    fun requestBookmark(single: SinglePostResponse?) {
        if (single == null) return

        AsyncTask.execute({
            val postTable = PostTable(single.id,
                    single.images,
                    single.title,
                    single.published, single.labels,
                    single.content,
                    single.url,
                    true)

            val update = postTableDao.add(postTable)
            if (update != 0L)
                singleLiveEvent.postValue(BookMark.SET)
        })

    }

    val singleLiveEvent = SingleLiveEvent<BookMark>()
    val searchData = remoteDataLayer.layerUtils.singleSearchData
    val searchEvent = remoteDataLayer.layerUtils.searchEvent

}