/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:10 AM
 */


package com.iamsdt.androidsketchpad.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.PAGE_TOKEN
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.SP_NAME

class SpUtils(val context: Context) {


    fun savePageToken(string: String) {
        sp.edit {
            putString(PAGE_TOKEN,string)
        }
    }

    val getPageToken:String get() = sp.getString(PAGE_TOKEN,"")


    private val sp: SharedPreferences
        get() =
            context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
}