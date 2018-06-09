/*
 * Created by Shudipto Trafder
 * on 6/9/18 12:58 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.singlePost

import com.google.gson.annotations.SerializedName
import com.iamsdt.androidsketchpad.data.retrofit.model.common.Author
import com.iamsdt.androidsketchpad.data.retrofit.model.common.Blog
import com.iamsdt.androidsketchpad.data.retrofit.model.common.ImagesItem

data class SinglePostResponse(@SerializedName("images")
                              val images: List<ImagesItem>?,
                              @SerializedName("kind")
                              val kind: String = "",
                              @SerializedName("author")
                              val author: Author,
                              @SerializedName("published")
                              val published: String = "",
                              @SerializedName("blog")
                              val blog: Blog,
                              @SerializedName("title")
                              val title: String = "",
                              @SerializedName("url")
                              val url: String = "",
                              @SerializedName("content")
                              val content: String = "",
                              @SerializedName("selfLink")
                              val selfLink: String = "",
                              @SerializedName("labels")
                              val labels: List<String>?,
                              @SerializedName("replies")
                              val replies: Replies,
                              @SerializedName("etag")
                              val etag: String = "",
                              @SerializedName("id")
                              val id: String = "",
                              @SerializedName("updated")
                              val updated: String = "")