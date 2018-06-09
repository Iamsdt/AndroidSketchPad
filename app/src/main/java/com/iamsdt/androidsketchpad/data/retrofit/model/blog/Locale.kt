/*
 * Created by Shudipto Trafder
 * on 6/9/18 1:03 PM
 */

package com.iamsdt.androidsketchpad.data.retrofit.model.blog

import com.google.gson.annotations.SerializedName

data class Locale(@SerializedName("country")
                  val country: String = "",
                  @SerializedName("variant")
                  val variant: String = "",
                  @SerializedName("language")
                  val language: String = "")