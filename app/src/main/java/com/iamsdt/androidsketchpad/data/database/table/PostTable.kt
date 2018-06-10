package com.iamsdt.androidsketchpad.data.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.iamsdt.androidsketchpad.data.retrofit.model.common.ImagesItem

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:44 AM
 */

@Entity
data class PostTable(
        @PrimaryKey
        var  id: String,

        var imgUrl:List<ImagesItem>?,

        var title:String,

        var published: String = "",

        val labels: List<String>?,

        val content: String = "",

        var bookmark:Boolean)