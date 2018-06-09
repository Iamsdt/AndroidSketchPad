package com.iamsdt.androidsketchpad.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.database.table.PageTable
import com.iamsdt.androidsketchpad.database.table.PostTable

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:43 AM
 */

@TypeConverters(DataConverter::class)
@Database(entities = [PageTable::class,PostTable::class],version = 1,
        exportSchema = false)
abstract class MyDB:RoomDatabase(){

    abstract val pageTableDao:PageTableDao
    abstract val postTableDao:PostTableDao

}