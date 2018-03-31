package com.iamsdt.androidsketchpad.injection

import com.iamsdt.androidsketchpad.injection.module.FragmentModule
import com.iamsdt.androidsketchpad.injection.scopes.FragmentScope
import dagger.Component

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:33 PM
 */


@FragmentScope
@Component(modules = [FragmentModule::class],
        dependencies = [ApplicationComponent::class])
interface FragmentComponent{
    //fun inject
}