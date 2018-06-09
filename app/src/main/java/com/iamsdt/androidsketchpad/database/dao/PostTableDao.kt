package com.iamsdt.androidsketchpad.database.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.*
import com.iamsdt.androidsketchpad.database.table.PostTable

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:44 AM
 */

@Dao
interface PostTableDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(postTable: PostTable):Long

    @Update
    fun update(postTable: PostTable):Int

    @Delete
    fun delete(postTable: PostTable):Int

    @get:Query("Select * From PostTable")
    val getAllPost: DataSource.Factory<Int, PostTable>

    @Query("Select * From PostTable where id = :id")
    fun getSinglePost(id:String):LiveData<PostTable>

    @get:Query("Select * From PostTable where bookmark = 1")
    val getBookmarkedPost:DataSource.Factory<Int, PostTable>

    @Query("Update PostTable set bookmark = 1 where id = :id")
    fun setBookmark(id: String):Long

    @Query("Update PostTable set bookmark = 0 where id = :id")
    fun deleteBookmark(id: String):Long
}