package com.iamsdt.androidsketchpad.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:44 AM
 */

@Entity
data class PostTable(
        @PrimaryKey(autoGenerate = true)
        var key: Int = 0,

        var  id: String,

        var nextPostToken:String,

        var imgUrl:String,

        var title:String,

        var published: String = "",

        val labels: String,

        val content: String = "",

        var authorId: String = "")