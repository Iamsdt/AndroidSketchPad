/*
 * Created by Shudipto Trafder
 * on 6/9/18 12:59 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.page

import com.google.gson.annotations.SerializedName

data class PageResponse(@SerializedName("kind")
                        val kind: String = "",
                        @SerializedName("etag")
                        val etag: String = "",
                        @SerializedName("items")
                        val items: List<ItemsItem>?)