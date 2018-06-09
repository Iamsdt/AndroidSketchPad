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

data class ItemsItem(@SerializedName("images")
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
                     @SerializedName("etag")
                     val etag: String = "",
                     @SerializedName("id")
                     val id: String = "",
                     @SerializedName("updated")
                     val updated: String = "")