/*
 * Created by Shudipto Trafder
 * on 6/9/18 6:21 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import com.iamsdt.androidsketchpad.data.retrofit.model.blog.BlogResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.common.Author
import com.iamsdt.androidsketchpad.data.retrofit.model.page.PageResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.database.table.PageTable
import com.iamsdt.androidsketchpad.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.SpUtils
import com.iamsdt.androidsketchpad.utils.ext.SingleLiveEvent
import com.iamsdt.androidsketchpad.utils.model.EventMessage
import kotlinx.coroutines.experimental.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LayerUtils(private val spUtils: SpUtils,
                 private val pageTableDao: PageTableDao,
                 private val postTableDao: PostTableDao){

    val serviceLiveData:SingleLiveEvent<EventMessage> = SingleLiveEvent()

    fun executePostCall(call: Call<PostsResponse>,token:String = ""){
        async {
            call.enqueue(object :Callback<PostsResponse>{
                override fun onFailure(call: Call<PostsResponse>?, t: Throwable?) {
                    Timber.i(t,"page data failed")
                }

                override fun onResponse(call: Call<PostsResponse>?, response: Response<PostsResponse>?) {

                    if (response != null && response.isSuccessful){
                        //save token
                        val data:PostsResponse = response.body()!!

                        spUtils.savePageToken(data.nextPageToken)
                        Timber.i("New post token${data.nextPageToken}")

                        //save used token
                        if (token.isNotEmpty()){
                            spUtils.saveUsedPageToken(token)
                            Timber.i("Used page token$token")
                        }

                        val list = data.items ?: emptyList()
                        var author:Author ?= null
                        for (post in list){
                            val postTable = PostTable(
                                    post.id,post.images,
                                    post.title,
                                    post.published,
                                    post.labels,
                                    post.content,
                                    false)

                            author = post.author

                            Timber.i("Add post: title:${post.title}")
                            postTableDao.add(postTable)
                        }
                        //save author
                        spUtils.saveAuthor(author)
                        Timber.i("Save author:${author?.displayName}")

                        //set RemoteDataLayer.isAlreadyRequested is false
                        // that's means I am ready for new request
                        RemoteDataLayer.isAlreadyRequested = false
                        Timber.i("Open for new request")
                    }
                }

            })
        }
    }

    fun executePageCall(call: Call<PageResponse>){
        async {
            call.enqueue(object :Callback<PageResponse>{
                override fun onFailure(call: Call<PageResponse>?, t: Throwable?) {
                    Timber.i(t,"page data failed")
                }

                override fun onResponse(call: Call<PageResponse>?,
                                        response: Response<PageResponse>?) {

                    if (response != null && response.isSuccessful){
                        //save token
                        val data:PageResponse = response.body()!!

                        val list = data.items ?: emptyList()
                        for (page in list){
                            val pageTable = PageTable(
                                    page.id,page.published,page.title,page.updated,
                                    page.url,page.content)

                            Timber.i("Adding page data ${page.title}")

                            pageTableDao.add(pageTable)

                        }
                    }
                }

            })
        }
    }

    fun executeBlogCall(call: Call<BlogResponse>){
        async {
            call.enqueue(object :Callback<BlogResponse>{
                override fun onFailure(call: Call<BlogResponse>?, t: Throwable?) {
                    Timber.i(t,"page data failed")
                }

                override fun onResponse(call: Call<BlogResponse>?,
                                        response: Response<BlogResponse>?) {

                    if (response != null && response.isSuccessful){
                        //save token
                        val data:BlogResponse = response.body()!!
                        spUtils.saveBlog(data)
                        Timber.i("Saved blog data ${data.name}")
                    }
                }

            })
        }
    }
}