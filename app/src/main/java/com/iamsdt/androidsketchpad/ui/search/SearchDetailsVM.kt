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

        AsyncTask.execute {

            var post: PostTable? = postTableDao.getPost(single.id)

            if (post == null) {
                post = PostTable(single.id,
                        single.images,
                        single.title,
                        single.published,
                        single.labels,
                        single.content,
                        single.url,
                        true)
            } else {
                post.title = single.title
                post.content = single.content
                post.published = single.published
                post.labels = single.labels
                post.url = single.url
                post.bookmark = true
            }


            val update = postTableDao.add(post)
            if (update != 0L)
                singleLiveEvent.postValue(BookMark.SET)
        }

    }

    val singleLiveEvent = SingleLiveEvent<BookMark>()
    val searchData = remoteDataLayer.layerUtils.singleSearchData
    val searchEvent = remoteDataLayer.layerUtils.searchEvent

}