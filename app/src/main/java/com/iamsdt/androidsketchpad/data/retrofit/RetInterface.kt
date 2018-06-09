package com.iamsdt.androidsketchpad.data.retrofit

import com.iamsdt.androidsketchpad.data.retrofit.model.blog.BlogResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.page.PageResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.singlePage.SinglePageResponse
import com.iamsdt.androidsketchpad.data.retrofit.model.singlePost.SinglePostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:45 AM
 */
interface RetInterface {

    @GET("/posts?fetchImages=true")
    fun getPostForFirstTime(@Query("key") key: String):Call<List<PostsResponse>>

    //posts/search?q=documentation
    @GET("/posts?fetchImages=true")
    fun getPostFormSearch(
            @Query("search") search: String,
            @Query("key") key: String):Call<List<PostsResponse>>

    @GET("/posts?fetchImages=true")
    fun getPostFormSearchWithToken(@Query("getPostWithToken") token: String,
                                   @Query("key") key: String):Call<List<PostsResponse>>

    //posts/postId
    @GET("/posts/{id}?fetchImages=true")
    fun getSinglePost(@Path("id") posID: Int,
                      @Query("key") key: String):Call<SinglePostResponse>

    ///pages?fetchImages=true&key
    @GET("/pages/?fetchImages=true")
    fun getPageList(@Query("key") key: String):Call<List<PageResponse>>

    @GET("/pages/{id}?fetchImages=true")
    fun getSinglePage(@Path("id") posID: Int,
                      @Query("key") key: String):Call<SinglePageResponse>

    @GET("/pageviews?range=all")
    fun getPageView(@Query("key") key: String)


    @GET
    fun getBlog():Call<BlogResponse>
}