/*
 * Created by Shudipto Trafder
 * on 6/9/18 1:04 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.singlePage

import com.google.gson.annotations.SerializedName

data class SinglePageResponse(@SerializedName("kind")
                              val kind: String = "",
                              @SerializedName("author")
                              val author: Author,
                              @SerializedName("etag")
                              val etag: String = "",
                              @SerializedName("id")
                              val id: String = "",
                              @SerializedName("published")
                              val published: String = "",
                              @SerializedName("blog")
                              val blog: Blog,
                              @SerializedName("title")
                              val title: String = "",
                              @SerializedName("updated")
                              val updated: String = "",
                              @SerializedName("url")
                              val url: String = "",
                              @SerializedName("content")
                              val content: String = "",
                              @SerializedName("selfLink")
                              val selfLink: String = "")