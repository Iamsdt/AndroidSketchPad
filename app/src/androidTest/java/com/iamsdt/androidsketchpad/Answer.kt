/*
 * Created by Shudipto Trafder
 * on 6/12/18 11:48 AM
 */

package com.iamsdt.androidsketchpad

import android.support.test.runner.AndroidJUnit4
import com.iamsdt.androidsketchpad.data.database.table.PostTable
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class Answer {


    class RetData {

        private lateinit var retrofit: Retrofit

        var wikiApiService: WikiApiService

        init {
            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                        .baseUrl("https://api.stackexchange.com/2.2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            wikiApiService = retrofit.create(WikiApiService::class.java)
        }
    }

    interface WikiApiService {

        @GET("api.php")
        fun hitCountCheck(@Query("action") action: String): Call<PostTable>
    }

    @Test
    fun test() {

        val retrofit = RetData()

        val apiService = retrofit.wikiApiService
        println(apiService)

        val apiService2 = retrofit.wikiApiService
        println(apiService2)

        val apiService3 = retrofit.wikiApiService
        println(apiService3)

        val apiService4 = retrofit.wikiApiService
        println(apiService4)
    }

}