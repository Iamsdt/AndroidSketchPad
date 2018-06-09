/*
 * Created by Shudipto Trafder
 * on 6/9/18 12:58 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.singlePost

import com.google.gson.annotations.SerializedName

data class Replies(@SerializedName("totalItems")
                   val totalItems: String = "",
                   @SerializedName("selfLink")
                   val selfLink: String = "")