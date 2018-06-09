package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.BuildConfig
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.data.retrofit.model.posts.PostsResponse
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.utils.ext.blockingObserve
import kotlinx.coroutines.experimental.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retInterface: RetInterface

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var dao:PageTableDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = retrofit.baseUrl()

        getData()

    }

    private fun getData() {
        async {
            retInterface.getPostForFirstTime(BuildConfig.BloggerApiKey)
                    .enqueue(object :Callback<PostsResponse>{
                        override fun onFailure(call: Call<PostsResponse>?, t: Throwable?) {
                            Timber.e(t,"data load failed")
                        }

                        override fun onResponse(call: Call<PostsResponse>?,
                                                response: Response<PostsResponse>?) {

                            Timber.i("response:$response")

                            if (response != null && response.isSuccessful){
                                val post =  response.body()


                                    Timber.i("Page Token: ${post?.nextPageToken}")

                                    val itemsItem = post?.items ?: emptyList()
                                    for (item in itemsItem){
                                        Timber.i("Post title: ${item.title}")
                                    }
                                }
                            }

                    })
        }
    }
}
