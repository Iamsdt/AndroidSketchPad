package com.iamsdt.androidsketchpad.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.database.table.PageTable
import com.iamsdt.androidsketchpad.database.table.PostTable

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:43 AM
 */

@Database(entities = [PageTable::class,PostTable::class],version = 1,
        exportSchema = false)
abstract class MyDB:RoomDatabase(){

    abstract val pageTableDao:PageTableDao
    abstract val postTableDao:PostTableDao

    companion object {
        private const val dbName = "ShokerSchool"


    }
}