/*
 * Created by Shudipto Trafder
 * on 6/12/18 9:27 PM
 */

package com.iamsdt.androidsketchpad.ui.page

import android.arch.lifecycle.ViewModel
import com.iamsdt.androidsketchpad.data.database.dao.PageTableDao
import javax.inject.Inject

class PageDetailsVM @Inject constructor(val pageTableDao: PageTableDao) :
        ViewModel() {


    fun getSinglePage(id: String) =
            pageTableDao.getSinglePage(id)

}