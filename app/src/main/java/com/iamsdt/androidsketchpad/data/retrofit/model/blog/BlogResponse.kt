/*
 * Created by Shudipto Trafder
 * on 6/9/18 1:03 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.blog

import com.google.gson.annotations.SerializedName

data class BlogResponse(@SerializedName("pages")
                        val pages: Pages,
                        @SerializedName("kind")
                        val kind: String = "",
                        @SerializedName("name")
                        val name: String = "",
                        @SerializedName("description")
                        val description: String = "",
                        @SerializedName("id")
                        val id: String = "",
                        @SerializedName("published")
                        val published: String = "",
                        @SerializedName("locale")
                        val locale: Locale,
                        @SerializedName("updated")
                        val updated: String = "",
                        @SerializedName("posts")
                        val posts: Posts,
                        @SerializedName("url")
                        val url: String = "",
                        @SerializedName("selfLink")
                        val selfLink: String = "")