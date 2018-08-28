/*
 * Created by Shudipto Trafder
 * on 6/9/18 6:21 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import android.os.AsyncTask
import android.os.Handler
import android.os.HandlerThread
import com.iamsdt.androidsketchpad.data.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.data.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.data.database.table.PageTable
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import com.iamsdt.androidsketchpad.data.retrofit.model.blog.BlogResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.common.Author
import com.iamsdt.androidsketchpad.data.retrofit.model.page.PageResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.singlePost.SinglePostResponse
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.BLOG_KEY
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.PAGE_KEY
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Event.POST_KEY
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.SingleLiveEvent
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LayerUtils(private val spUtils: SpUtils,
                 private val pageTableDao: PageTableDao,
                 private val postTableDao: PostTableDao) {

    val serviceLiveData: SingleLiveEvent<EventMessage> = SingleLiveEvent()
    val uiLIveEvent: SingleLiveEvent<EventMessage> = SingleLiveEvent()

    //search related
    val searchEvent: SingleLiveEvent<EventMessage> = SingleLiveEvent()
    val searchData: SingleLiveEvent<PostsResponse> = SingleLiveEvent()
    val singleSearchData: SingleLiveEvent<SinglePostResponse> = SingleLiveEvent()

    fun executePostCall(call: Call<PostsResponse>, token: String = "",
                        isCalledFromService: Boolean) {

        val thread = HandlerThread("Postcall")
        thread.start()
        Handler(thread.looper).post {
            call.enqueue(object : Callback<PostsResponse> {
                override fun onFailure(call: Call<PostsResponse>?, t: Throwable?) {
                    Timber.i(t, "page data failed")
                    serviceLiveData.postValue(EventMessage(POST_KEY, token, 0))
                }

                override fun onResponse(call: Call<PostsResponse>?, response: Response<PostsResponse>?) {

                    if (response != null && response.isSuccessful) {
                        //save token
                        val data: PostsResponse ?= response.body()


                        if (data?.nextPageToken?.isNotEmpty() == true){
                            spUtils.savePageToken(data.nextPageToken)
                        }

                        if (token.isNotEmpty()){
                            spUtils.saveUsedPageToken(token)
                        }

                        Timber.i("New post token${data?.nextPageToken}")

                        val list = data?.items ?: emptyList()
                        AsyncTask.execute {
                            var author: Author? = null
                            var inserted: Long = 0
                            for (post in list) {
                                val postTable = PostTable(
                                        post.id, post.images,
                                        post.title,
                                        post.published,
                                        post.labels,
                                        post.content,
                                        post.url,
                                        false)

                                author = post.author

                                Timber.i("Add post: title:${post.title}")
                                inserted += postTableDao.add(postTable)
                            }

                            if (inserted != 0L && !isCalledFromService) {
                                uiLIveEvent.postValue(EventMessage(POST_KEY, "complete", 1))
                            }

                            Timber.i("Inserted:$inserted")
                            //save author
                            spUtils.saveAuthor(author)
                            Timber.i("Save author:${author?.displayName}")
                        }


                        //set RemoteDataLayer.isAlreadyRequested is false
                        // that's means I am ready for new request
                        RemoteDataLayer.isAlreadyRequested = false
                        Timber.i("Open for new request")
                        if (isCalledFromService)
                            serviceLiveData.postValue(EventMessage(POST_KEY, token, 1))
                    } else {
                        if (isCalledFromService)
                            serviceLiveData.postValue(EventMessage(POST_KEY, token, 0))
                        else
                            uiLIveEvent.postValue(EventMessage(POST_KEY, "complete", 0))

                    }
                }

            })
            thread.quitSafely()
        }
    }

    fun executePageCall(call: Call<PageResponse>) {
        val thread = HandlerThread("PageCall")
        thread.start()
        Handler(thread.looper).post {
            call.enqueue(object : Callback<PageResponse> {
                override fun onFailure(call: Call<PageResponse>?, t: Throwable?) {
                    Timber.i(t, "page data failed")
                    serviceLiveData.postValue(EventMessage(PAGE_KEY, "failed", 0))
                }

                override fun onResponse(call: Call<PageResponse>?,
                                        response: Response<PageResponse>?) {
                    if (response != null && response.isSuccessful) {
                        //save token
                        val data: PageResponse ?= response.body()

                        val list = data?.items ?: emptyList()
                        AsyncTask.execute {
                            var inserted: Long = 0
                            for (page in list) {
                                //don't use this value
                                if (page.title == "Sitemap" || page.title == "Contact us")
                                    continue
                                val pageTable = PageTable(
                                        page.id, page.published, page.title, page.updated,
                                        page.url, page.content)

                                Timber.i("Adding page data ${page.title}")

                                inserted += pageTableDao.add(pageTable)
                            }
                            if (inserted != 0L) {
                                serviceLiveData.postValue(EventMessage(PAGE_KEY, "Success", 1))
                            }
                        }
                    } else {
                        serviceLiveData.postValue(EventMessage(PAGE_KEY, "failed", 0))
                    }


                }
            })
            thread.quitSafely()
        }
    }

    fun executeBlogCall(call: Call<BlogResponse>) {
        val thread = HandlerThread("BlogCall")
        thread.start()
        Handler(thread.looper).post {
            call.enqueue(object : Callback<BlogResponse> {
                override fun onFailure(call: Call<BlogResponse>?, t: Throwable?) {
                    Timber.i(t, "blog data failed")
                    serviceLiveData.postValue(EventMessage(BLOG_KEY, "failed: $t", 0))
                    uiLIveEvent.postValue(EventMessage(BLOG_KEY, "failed: $t", 0))
                }

                override fun onResponse(call: Call<BlogResponse>?,
                                        response: Response<BlogResponse>?) {

                    if (response != null && response.isSuccessful) {
                        //save token
                        val data: BlogResponse = response.body()!!
                        spUtils.saveBlog(data)
                        Timber.i("Saved blog data ${data.name}")
                        if (data.name.isNotEmpty()) {
                            serviceLiveData.postValue(EventMessage(BLOG_KEY, "Success", 1))
                            uiLIveEvent.postValue(EventMessage(BLOG_KEY, "Success", 1))
                        }
                    } else {
                        serviceLiveData.postValue(EventMessage(BLOG_KEY, "failed", 0))
                        uiLIveEvent.postValue(EventMessage(BLOG_KEY, "failed", 0))
                    }
                }

            })
            thread.quitSafely()
        }

    }


    //i don't want to save into database
    // this is only called form activity
    fun executeSearchCall(call: Call<PostsResponse>, token: String = "") {

        AsyncTask.execute {
            call.enqueue(object : Callback<PostsResponse> {
                override fun onFailure(call: Call<PostsResponse>?, t: Throwable?) {
                    Timber.i(t, "page data failed")
                    searchEvent.postValue(EventMessage(POST_KEY, token, 0))
                }

                override fun onResponse(call: Call<PostsResponse>?, response: Response<PostsResponse>?) {

                    if (response != null && response.isSuccessful) {

                        val data: PostsResponse ?= response.body()

                        Timber.i("New post token${data?.nextPageToken}")

                        searchData.postValue(data)

                        searchEvent.postValue(EventMessage(POST_KEY, "${data?.items?.size ?: 0}", 1))
                    } else {
                        searchEvent.postValue(EventMessage(POST_KEY, "complete", 0))
                    }
                }

            })
        }
    }

    fun executeSinglePageCall(call: Call<SinglePostResponse>) {
        AsyncTask.execute {
            call.enqueue(object : Callback<SinglePostResponse> {
                override fun onFailure(call: Call<SinglePostResponse>?, t: Throwable?) {
                    Timber.i(t, "page data failed")
                    searchEvent.postValue(EventMessage(POST_KEY,"Failed", 0))
                }

                override fun onResponse(call: Call<SinglePostResponse>?, response: Response<SinglePostResponse>?) {
                    if (response != null && response.isSuccessful) {

                        val data = response.body()!!

                        singleSearchData.postValue(data)

                        searchEvent.postValue(EventMessage(POST_KEY, "", 1))

                    } else {
                        searchEvent.postValue(EventMessage(POST_KEY, "complete", 0))
                    }
                }

            })
        }
    }
}