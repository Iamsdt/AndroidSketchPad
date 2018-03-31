package com.iamsdt.androidsketchpad.injection.module

import com.iamsdt.androidsketchpad.injection.scopes.FragmentScope
import com.iamsdt.androidsketchpad.ui.base.BaseFragment
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:24 PM
 */

@Module
class FragmentModule(private val baseFragment: BaseFragment){

    @Provides
    @FragmentScope
    fun provideFragment(): BaseFragment
            = baseFragment

}