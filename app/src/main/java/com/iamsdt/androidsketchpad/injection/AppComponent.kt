package com.iamsdt.androidsketchpad.injection

import android.app.Application
import com.iamsdt.androidsketchpad.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 * Created by Shudipto Trafder on 3/31/2018.
 * at 11:48 AM
 */

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    PicassoModule::class,
    NetworkModule::class,
    RetrofitModule::class,
    EventBusModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    AppModule::class])
interface AppComponent:AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}