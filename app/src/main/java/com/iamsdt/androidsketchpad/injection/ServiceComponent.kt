package com.iamsdt.androidsketchpad.injection

import com.iamsdt.androidsketchpad.injection.module.ServiceModule
import com.iamsdt.androidsketchpad.injection.scopes.ServiceScope
import dagger.Component

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 6:30 PM
 */

@ServiceScope
@Component(modules = [ServiceModule::class],
        dependencies = [ApplicationComponent::class])
interface ServiceComponent{

    //fun inject()

}