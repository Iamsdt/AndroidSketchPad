package com.iamsdt.androidsketchpad.injection.module

import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:50 AM
 */

@Module
class EventBusModule{

    @Provides
    @Singleton
    fun getBus():EventBus = EventBus.builder()
            .sendSubscriberExceptionEvent(true)
            .installDefaultEventBus()

}