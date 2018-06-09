/*
 * Created by Shudipto Trafder
 * on 6/9/18 1:03 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.blog

import com.google.gson.annotations.SerializedName

data class Pages(@SerializedName("totalItems")
                 val totalItems: Int = 0,
                 @SerializedName("selfLink")
                 val selfLink: String = "")