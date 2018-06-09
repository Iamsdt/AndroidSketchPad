/*
 * Created by Shudipto Trafder
 * on 6/9/18 6:21 PM
 */

package com.iamsdt.androidsketchpad.data.loader

import com.iamsdt.androidsketchpad.data.retrofit.model.blog.BlogResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.page.PageResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.database.table.PageTable
import com.iamsdt.androidsketchpad.database.table.PostTable
import com.iamsdt.androidsketchpad.utils.SpUtils
import kotlinx.coroutines.experimental.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LayerUtils(val spUtils: SpUtils,
                 val pageTableDao: PageTableDao,
                 val postTableDao: PostTableDao){

    fun executePostCall(call: Call<PostsResponse>){
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

                        val list = data.items ?: emptyList()
                        for (post in list){
                            val postTable = PostTable(
                                    post.id,post.images,
                                    post.title,
                                    post.published,
                                    post.labels,
                                    post.content,
                                    false)

                            postTableDao.add(postTable)
                        }
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
                    }
                }

            })
        }
    }
}