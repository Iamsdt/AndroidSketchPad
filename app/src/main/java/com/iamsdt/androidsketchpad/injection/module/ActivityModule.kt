/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:05 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import com.iamsdt.androidsketchpad.ui.AboutBlogActivity
import com.iamsdt.androidsketchpad.ui.SplashScreen
import com.iamsdt.androidsketchpad.ui.bookmark.BookmarkActivity
import com.iamsdt.androidsketchpad.ui.details.DetailsActivity
import com.iamsdt.androidsketchpad.ui.main.MainActivity
import com.iamsdt.androidsketchpad.ui.page.PageActivity
import com.iamsdt.androidsketchpad.ui.page.PageDetailsActivity
import com.iamsdt.androidsketchpad.ui.services.UpdateService
import com.iamsdt.androidsketchpad.utils.ConnectivityChangeReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun getSplashActivity(): SplashScreen

    @ContributesAndroidInjector
    abstract fun getMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun DetailsActivity(): DetailsActivity

    @ContributesAndroidInjector
    abstract fun BookmarkActivity(): BookmarkActivity


    @ContributesAndroidInjector
    abstract fun PageActivity(): PageActivity

    @ContributesAndroidInjector
    abstract fun PageDetailsActivity(): PageDetailsActivity

    @ContributesAndroidInjector
    abstract fun AboutBlogActivity(): AboutBlogActivity

    @ContributesAndroidInjector
    abstract fun updateServices(): UpdateService

    @ContributesAndroidInjector
    abstract fun connectionReciver(): ConnectivityChangeReceiver
}