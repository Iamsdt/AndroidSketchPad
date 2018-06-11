/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:09 AM
 */

package com.iamsdt.androidsketchpad.injection.module


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.iamsdt.androidsketchpad.injection.scopes.ViewModelKey
import com.iamsdt.androidsketchpad.ui.details.DetailsVM
import com.iamsdt.androidsketchpad.ui.main.MainVM
import com.iamsdt.androidsketchpad.utils.ext.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainVM::class)
    internal abstract fun bindMainVM(vm: MainVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsVM::class)
    internal abstract fun detailsVM(vm: DetailsVM): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory)
            : ViewModelProvider.Factory
}