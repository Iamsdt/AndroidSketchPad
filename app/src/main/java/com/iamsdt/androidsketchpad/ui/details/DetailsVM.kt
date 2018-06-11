/*
 * Created by Shudipto Trafder
 * on 6/11/18 12:03 AM
 */

package com.iamsdt.androidsketchpad.ui.details

import android.arch.lifecycle.ViewModel
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import javax.inject.Inject

class DetailsVM @Inject constructor(val postTableDao: PostTableDao)
    : ViewModel() {

    fun getDetails(id: String) =
            postTableDao.getSinglePost(id)
}