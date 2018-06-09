/*
 * Created by Shudipto Trafder
 * on 6/9/18 1:04 PM
 */

/*
 * Created by Shudipto Trafder
 * on 6/9/18 12:53 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.posts

import com.google.gson.annotations.SerializedName

data class Author(@SerializedName("image")
                  val image: Image,
                  @SerializedName("displayName")
                  val displayName: String = "",
                  @SerializedName("id")
                  val id: String = "",
                  @SerializedName("url")
                  val url: String = "")