package com.iamsdt.androidsketchpad.injection.module

import com.iamsdt.androidsketchpad.injection.scopes.ServiceScope
import com.iamsdt.androidsketchpad.ui.base.BaseService
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:27 PM
 */

@Module
class ServiceModule(private val baseService: BaseService){

    @Provides
    @ServiceScope
    fun getServices(): BaseService
            = baseService
}