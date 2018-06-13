/*
 * Created by Shudipto Trafder
 * on 6/9/18 5:09 PM
 */

package com.iamsdt.androidsketchpad.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.androidsketchpad.data.database.table.PageTable

@Dao
interface PageTableDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(pageTable: PageTable):Long

    @Update
    fun update(pageTable: PageTable):Int

    @Delete
    fun delete(pageTable: PageTable):Int

    @get:Query("Select * From PageTable")
    val getAllPage: LiveData<List<PageTable>>

    @Query("Select * From PageTable where id = :id")
    fun getSinglePage(id:String): LiveData<PageTable>

}