/*
 * Created by Shudipto Trafder
 * on 6/9/18 12:58 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.singlePost

import com.google.gson.annotations.SerializedName

data class Author(@SerializedName("image")
                  val image: Image,
                  @SerializedName("displayName")
                  val displayName: String = "",
                  @SerializedName("id")
                  val id: String = "",
                  @SerializedName("url")
                  val url: String = "")