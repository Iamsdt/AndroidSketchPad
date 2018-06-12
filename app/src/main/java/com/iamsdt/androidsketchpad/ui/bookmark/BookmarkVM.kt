/*
 * Created by Shudipto Trafder
 * on 6/12/18 12:19 PM
 */

package com.iamsdt.androidsketchpad.ui.bookmark

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import javax.inject.Inject

class BookmarkVM @Inject constructor(
        val postTableDao: PostTableDao) : ViewModel() {


    fun getBookmarkData(): LiveData<PagedList<PostTable>> {

        val source = postTableDao.getBookmarkedPost

        val config = PagedList.Config.Builder()
                .setPageSize(7)
                .setInitialLoadSizeHint(10)//by default page size * 3
                .setPrefetchDistance(5) // default page size
                .setEnablePlaceholders(true) //default true
                // that's means scroll bar is not jump and all data set show on the
                //recycler view first after 30 it will show empty view
                // when load it will update with animation
                .build()

        return LivePagedListBuilder(source, config).build()
    }

}