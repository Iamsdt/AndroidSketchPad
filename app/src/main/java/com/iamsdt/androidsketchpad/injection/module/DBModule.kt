/*
 * Created by Shudipto Trafder
 * on 6/9/18 5:15 PM
 */

package com.iamsdt.androidsketchpad.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import com.iamsdt.androidsketchpad.database.MyDB
import com.iamsdt.androidsketchpad.database.dao.PageTableDao
import com.iamsdt.androidsketchpad.database.dao.PostTableDao
import com.iamsdt.androidsketchpad.utils.ConstantUtils.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule{

    @Provides
    @Singleton
    fun getPageDao(myDB: MyDB):PageTableDao =
            myDB.pageTableDao


    @Provides
    @Singleton
    fun getPostDao(myDB: MyDB):PostTableDao =
            myDB.postTableDao


    @Provides
    @Singleton
    fun getDb(context:Application):MyDB = Room.databaseBuilder(context,
            MyDB::class.java, DB_NAME).build()
}