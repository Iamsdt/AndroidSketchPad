/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:10 AM
 */


package com.iamsdt.androidsketchpad.utils

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager
import androidx.core.content.edit
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.retrofit.model.blog.BlogResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.common.Author
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Author.DISPLAY_NAME
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Author.ID
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Author.IMAGEURL
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Author.URL
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.DES
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.LOCALE_COUN
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.LOCALE_LAN
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.NAME
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.PAGE
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.POSTS
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.PUBLISHED
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Blog.UPDATE
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.APP_RUN_FIRST_TIME
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.BLOG_UPDATE
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.PAGE_TOKEN
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.SERVICE_RUN_DATE
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.SERVICE_RUN_FIRST_TIME
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.SP_NAME
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.USED_PAGE_TOKEN
import com.iamsdt.androidsketchpad.utils.model.AuthorModel
import com.iamsdt.androidsketchpad.utils.model.BlogModel
import timber.log.Timber

class SpUtils(private val context: Context) {


    /** Application related  SharedPreferences */
    val isFirstTime get() = sp.getBoolean(APP_RUN_FIRST_TIME, true)

    fun setAppRunFirstTime() {
        sp.edit {
            putBoolean(APP_RUN_FIRST_TIME, false)
        }
    }

    val isServiceFirstTime get() = sp.getBoolean(SERVICE_RUN_FIRST_TIME, true)

    fun setServiceRunFirstTime() {
        sp.edit {
            putBoolean(SERVICE_RUN_FIRST_TIME, false)
        }
    }

    val getBlogUpdate:Long get() = sp.getLong(BLOG_UPDATE, 0L)

    fun setBlogUpdate() {
        sp.edit {
            putLong(BLOG_UPDATE, DateUtils.currentDate())
        }
    }

    val getServiceRunDate:Long get() = sp.getLong(SERVICE_RUN_DATE, 0L)

    fun setServiceRunDate() {
        sp.edit {
            putLong(SERVICE_RUN_DATE, DateUtils.currentDate())
        }
    }

    fun saveUsedPageToken(string: String) {
        sp.edit {
            putString(USED_PAGE_TOKEN, string)
        }
    }

    val getUesedPageToken: String get() = sp.getString(USED_PAGE_TOKEN, "")

    fun savePageToken(string: String) {
        sp.edit {
            putString(PAGE_TOKEN, string)
        }
    }

    val getPageToken: String get() = sp.getString(PAGE_TOKEN, "")

    private val sp: SharedPreferences
        get() =
            context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    /** Settings related SharedPreferences */
    fun syncTimeInterval(context: Context):Int =
            settings.getString(context.getString(R.string.list_key),
                    context.getString(R.string.week1_value)).toInt()

    val settings:SharedPreferences get() = PreferenceManager
            .getDefaultSharedPreferences(context)


    /** Blog details  SharedPreferences */

    fun checkBlogData():Boolean =  blogSp.getString(NAME, "").isNotEmpty()

    fun getBlog(): BlogModel {
        val page = blogSp.getString(PAGE, "")
        val name = blogSp.getString(NAME, "")
        val des = blogSp.getString(DES, "")
        val published = blogSp.getString(PUBLISHED, "")
        val update = blogSp.getString(UPDATE, "")
        val language = blogSp.getString(LOCALE_LAN, "")
        val country = blogSp.getString(LOCALE_COUN, "")
        val post = blogSp.getString(POSTS, "")

        return BlogModel(page, name, des, published, update, language, country, post)
    }

    fun saveBlog(blog: BlogResponse) {
        blogSp.edit {
            putString(PAGE, blog.pages.totalItems.toString())
            putString(NAME, blog.name)
            putString(DES, blog.description)
            putString(PUBLISHED, blog.published)
            putString(UPDATE, blog.updated)
            putString(LOCALE_COUN, blog.locale.country)
            putString(LOCALE_LAN, blog.locale.language)
            putString(POSTS, blog.posts.totalItems.toString())
        }
    }

    private val blogSp: SharedPreferences
        get() =
            context.getSharedPreferences(ConstantUtils.Blog.SP_NAME, Context.MODE_PRIVATE)

    /** Author details  SharedPreferences */
    fun saveAuthor(author: Author?) {
        if (author != null) {
            authorSP.edit {
                putString(IMAGEURL, author.image.url)
                putString(DISPLAY_NAME, author.displayName)
                putString(ID, author.id)
                putString(URL, author.url)
                putString(DES, RemoteUtils.getAuthorDes())
            }
        }
    }

    fun getAuthor(): AuthorModel {
        val imageUrl: String = authorSP.getString(IMAGEURL, "")
        Timber.i("Author image link $imageUrl")
        val displayName: String = authorSP.getString(DISPLAY_NAME, "Shudipto Trafder")
        val id: String = authorSP.getString(ID, "")
        val url: String = authorSP.getString(URL, "")
        val des: String = authorSP.getString(DES, "")

        return AuthorModel(imageUrl, displayName, id, url, des)
    }

    private val authorSP: SharedPreferences
        get() =
            context.getSharedPreferences(ConstantUtils.Author.SP_NAME, Context.MODE_PRIVATE)

}