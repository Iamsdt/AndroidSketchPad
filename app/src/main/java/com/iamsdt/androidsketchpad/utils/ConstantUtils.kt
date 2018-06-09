package com.iamsdt.androidsketchpad.utils

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 5:52 PM
 */

class ConstantUtils{

    object Retrofit{
        val baseUrl:String = "https://www.googleapis.com/blogger/v3/blogs/blogId/"
    }

    companion object {
        //network
        const val internet = "internet"
        const val connected = "connected"
        const val noInternet = "noInternet"
    }
}