/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:18 AM
 */

package com.iamsdt.androidsketchpad.ui.main

import android.arch.lifecycle.ViewModel
import com.iamsdt.androidsketchpad.data.loader.RemoteDataLayer
import javax.inject.Inject

class MainVM @Inject constructor(val dataLayer: RemoteDataLayer):ViewModel(){

    fun getData(){

    }

}