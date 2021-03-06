/*
 * Created by Shudipto Trafder
 * on 6/11/18 12:03 AM
 */

package com.iamsdt.androidsketchpad.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.ext.BookMark
import com.iamsdt.androidsketchpad.utils.ext.SingleLiveEvent
import javax.inject.Inject

class DetailsVM @Inject constructor(val postTableDao: PostTableDao)
    : ViewModel() {

    val singleLiveEvent = SingleLiveEvent<BookMark>()

    fun getDetails(id: String) =
            postTableDao.getSinglePost(id)

    fun getRandomPost():LiveData<List<PostTable>>{
        val liveData = MutableLiveData<List<PostTable>>()

        AsyncTask.execute({
            val list = postTableDao.randomPost()
            liveData.postValue(list)
        })

        return liveData
    }

    private fun setBookmark(id: String) {
        AsyncTask.execute({
            val update = postTableDao.setBookmark(id)
            if (update != -1)
                singleLiveEvent.postValue(BookMark.SET)
        })
    }

    private fun deleteBookmark(id: String) {
        AsyncTask.execute({
            val delete = postTableDao.deleteBookmark(id)
            if (delete != -1)
                singleLiveEvent.postValue(BookMark.DELETE)
        })
    }

    fun requestBookmark(id: String, bookmarked: Boolean) {
        if (bookmarked)
            deleteBookmark(id)
        else
            setBookmark(id)
    }

    fun requestChangeIcon(boolean: Boolean){
        if (boolean){
            singleLiveEvent.value = (BookMark.SET)
        } else{
            singleLiveEvent.value = (BookMark.DELETE)
        }
    }


}