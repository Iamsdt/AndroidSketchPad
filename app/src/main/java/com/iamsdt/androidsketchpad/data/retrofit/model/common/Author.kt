/*
 * Created by Shudipto Trafder
 * on 6/9/18 1:53 PM
 */

/*
 * Created by Shudipto Trafder
 * on 6/9/18 12:59 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.common

import com.google.gson.annotations.SerializedName

data class Author(@SerializedName("image")
                  val image: Image,
                  @SerializedName("displayName")
                  val displayName: String = "",
                  @SerializedName("id")
                  val id: String = "",
                  @SerializedName("url")
                  val url: String = "")