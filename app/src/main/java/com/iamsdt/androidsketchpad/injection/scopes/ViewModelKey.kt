/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:08 AM
 */

package com.iamsdt.androidsketchpad.injection.scopes

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_SETTER,AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)