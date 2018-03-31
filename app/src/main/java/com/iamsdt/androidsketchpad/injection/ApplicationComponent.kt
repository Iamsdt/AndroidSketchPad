package com.iamsdt.androidsketchpad.injection

import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.iamsdt.androidsketchpad.injection.module.EventBusModule
import com.iamsdt.androidsketchpad.injection.module.PicassoModule
import com.iamsdt.androidsketchpad.injection.module.RetrofitModule
import com.iamsdt.androidsketchpad.injection.scopes.AppScope
import com.squareup.picasso.Picasso
import dagger.Component
import org.greenrobot.eventbus.EventBus

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:48 AM
 */

@AppScope
@Component(modules = [PicassoModule::class,
    RetrofitModule::class,
    EventBusModule::class])
interface ApplicationComponent{

    //picasso
    val picasso: Picasso

    //retrofit
    //val retrofitHandler:RetrofitHandler
    val wpRestInterface:RetInterface

    //val event bus
    val eventBus: EventBus

}