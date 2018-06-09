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

    @GET("/blogger/v3/blogs/141872694236847267/posts?fetchImages=true")
    fun getPostForFirstTime(@Query("key") key: String):Call<PostsResponse>

    //posts/search?q=documentation
    @GET("/blogger/v3/blogs/141872694236847267/posts?fetchImages=true")
    fun getPostFormSearch(
            @Query("search") search: String,
            @Query("key") key: String):Call<PostsResponse>

    @GET("/blogger/v3/blogs/141872694236847267/posts?fetchImages=true")
    fun getPostWithToken(@Query("getPostWithToken") token: String,
                                   @Query("key") key: String):Call<PostsResponse>

    //posts/postId
    @GET("/blogger/v3/blogs/141872694236847267/posts/{id}?fetchImages=true")
    fun getSinglePost(@Path("id") posID: Int,
                      @Query("key") key: String):Call<SinglePostResponse>

    ///pages?fetchImages=true&key
    @GET("/blogger/v3/blogs/141872694236847267/pages/?fetchImages=true")
    fun getPageList(@Query("key") key: String):Call<PageResponse>

    @GET("/blogger/v3/blogs/141872694236847267/pages/{id}?fetchImages=true")
    fun getSinglePage(@Path("id") posID: Int,
                      @Query("key") key: String):Call<SinglePageResponse>

    @GET("/blogger/v3/blogs/141872694236847267/pageviews?range=all")
    fun getPageView(@Query("key") key: String)


    @GET("/blogger/v3/blogs/141872694236847267")
    fun getBlog():Call<BlogResponse>
}