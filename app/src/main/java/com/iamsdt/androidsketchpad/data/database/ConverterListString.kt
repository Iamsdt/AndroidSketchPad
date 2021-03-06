/*
 * Created by Shudipto Trafder
 * on 6/10/18 6:59 PM
 */

package com.iamsdt.androidsketchpad.data.database

import android.arch.persistence.room.TypeConverter

class ConverterListString{
    @TypeConverter
    fun toList(string: String?):List<String>{
        val list = arrayListOf<String>()
        if (string != null &&string.contains(",")){
            val strings = string.split(",")
            for (li in strings){
                list.add(li)
            }
        }

        return list
    }

    @TypeConverter
    fun toString(list: List<String>?):String{
        val string = StringBuilder()
        val newList = list ?: emptyList()
        for (s in newList){
            string.append("$s,")
        }

        return string.toString()
    }
}