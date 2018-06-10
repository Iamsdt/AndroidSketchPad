/*
 * Created by Shudipto Trafder
 * on 6/9/18 2:56 PM
 */

package com.iamsdt.androidsketchpad.data.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class PageTable(
        @PrimaryKey
        var id: String = "",
        var published: String = "",
        var title: String = "",
        var updated: String = "",
        var url: String = "",
        var content: String = "")