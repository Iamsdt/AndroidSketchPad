package com.iamsdt.androidsketchpad.injection.module

import android.content.Context
import com.iamsdt.androidsketchpad.injection.scopes.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:55 AM
 */

@Module
internal class ContextModule(context: Context) {

    private val mContext = context

    @Provides
    @AppScope
    fun getContext(): Context = mContext
}