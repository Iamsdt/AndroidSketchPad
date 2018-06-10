/*
 * Created by Shudipto Trafder
 * on 6/9/18 6:48 PM
 */

package com.iamsdt.androidsketchpad.data.database
import android.arch.persistence.room.TypeConverter
import com.iamsdt.androidsketchpad.data.retrofit.model.common.ImagesItem
import kotlin.text.StringBuilder

class DataConverter{


    @TypeConverter
    fun toImageString(list: List<ImagesItem>?):String{
        val string = StringBuilder()
        val newList = list ?: emptyList()
        for (s in newList){
            string.append("${s.url},")
        }

        return string.toString()
    }

    @TypeConverter
    fun toImageList(string: String?):List<ImagesItem>{
        val list = arrayListOf<ImagesItem>()

        if (string != null &&string.contains(",")){
            val strings = string.split(",")
            for (li in strings){
                val imagesItem = ImagesItem(li)
                list.add(imagesItem)
            }
        }

        return list
    }

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