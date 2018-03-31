package com.iamsdt.androidsketchpad.injection

import com.iamsdt.androidsketchpad.injection.module.ActivityModule
import com.iamsdt.androidsketchpad.injection.scopes.ActivityScope
import com.iamsdt.androidsketchpad.ui.MainActivity
import dagger.Component

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:31 PM
 */

@ActivityScope
@Component(modules = [ActivityModule::class],
        dependencies = [ApplicationComponent::class])
interface ActivityComponent{

    fun inject(mainActivity: MainActivity)

}