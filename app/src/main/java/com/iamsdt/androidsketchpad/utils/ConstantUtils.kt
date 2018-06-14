package com.iamsdt.androidsketchpad.utils

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 5:52 PM
 */

class ConstantUtils{

    companion object {

        const val DB_NAME = "AndroidSketchPad"

        //sp
        const val SP_NAME = "APP_SP"
        const val PAGE_TOKEN = "page_token"
        const val USED_PAGE_TOKEN = "used_page_token"
        const val APP_RUN_FIRST_TIME = "firstTime"
        const val SERVICE_RUN_FIRST_TIME = "ServiceFirstTime"
        const val SERVICE_RUN_DATE= "serviceDate"
        const val BLOG_UPDATE= "blogUpdate"

        //network
        const val internet = "internet"
        const val connected = "connected"
        const val noInternet = "noInternet"
    }


    object Event{
        //post call
        const val POST_KEY = "PostCall"
        const val BLOG_KEY = "BLOGCall"
        const val PAGE_KEY = "PageCall"

        const val SERVICE = "SERVICE"

        const val POST_WITH_TOKEN = "postToken"
    }

    //blog sp
    object Blog{
        const val SP_NAME = "BLOG_SP"

        const val PAGE = "page"
        const val NAME = "name"
        const val DES = "des"
        const val PUBLISHED = "pub"
        const val UPDATE = "update"
        const val LOCALE_LAN = "localeLan"
        const val LOCALE_COUN = "localeCon"
        const val POSTS = "posts"

    }

    object Author{
        const val SP_NAME = "Author"
        const val IMAGEURL = "imageUrl"
        const val DISPLAY_NAME = "displayName"
        const val ID = "id"
        const val URL = "url"
        const val DES = "des"
    }
}