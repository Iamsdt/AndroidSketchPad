/*
 * Created by Shudipto Trafder
 * on 6/9/18 9:27 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.iamsdt.androidsketchpad.data.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PageTable
import com.iamsdt.androidsketchpad.utils.SpUtils
import timber.log.Timber


//not use any more
class DataLayer(private val remoteDataLayer: RemoteDataLayer,
                private val postTableDao: PostTableDao,
                private val pageTableDao: PageTableDao,
                private val spUtils: SpUtils) {



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
}