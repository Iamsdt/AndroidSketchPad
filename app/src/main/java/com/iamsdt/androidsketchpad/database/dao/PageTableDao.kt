/*
 * Created by Shudipto Trafder
 * on 6/9/18 5:09 PM
 */

package com.iamsdt.androidsketchpad.database.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.arch.persistence.room.*
import com.iamsdt.androidsketchpad.database.table.PageTable
import com.iamsdt.androidsketchpad.database.table.PostTable

@Dao
interface PageTableDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(pageTable: PageTable):Long

    @Update
    fun update(pageTable: PageTable):Long

    @Delete
    fun delete(pageTable: PageTable):Long

    @get:Query("Select * From PageTable")
    val getAllPage: LiveData<PageTable>

    @Query("Select * From PageTable where id = :id")
    fun getSinglePage(id:String): LiveData<PageTable>

}