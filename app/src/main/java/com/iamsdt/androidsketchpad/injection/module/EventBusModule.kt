package com.iamsdt.androidsketchpad.injection.module

import com.iamsdt.androidsketchpad.injection.scopes.AppScope
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:50 AM
 */

@Module
internal class EventBusModule{

    @Provides
    @AppScope
    fun getBus() = EventBus.builder()
            .sendSubscriberExceptionEvent(true)
            .installDefaultEventBus()

}