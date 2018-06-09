/*
 * Created by Shudipto Trafder
 * on 6/9/18 2:46 PM
 */

package com.iamsdt.androidsketchpad.database.table

import android.arch.persistence.room.PrimaryKey

data class AuthorTable(
        var imageUrl: String,
        var displayName: String = "",
        @PrimaryKey
        var id: String = "",
        var url: String = "")