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

data class PostsResponse(@SerializedName("kind")
                         val kind: String = "",
                         @SerializedName("nextPageToken")
                         val nextPageToken: String = "",
                         @SerializedName("etag")
                         val etag: String = "",
                         @SerializedName("items")
                         val items: List<ItemsItem>?)