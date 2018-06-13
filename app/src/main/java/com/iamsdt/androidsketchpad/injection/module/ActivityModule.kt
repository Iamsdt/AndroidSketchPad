/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:05 AM
 */

package com.iamsdt.androidsketchpad.injection.module

import com.iamsdt.androidsketchpad.ui.AboutApp
import com.iamsdt.androidsketchpad.ui.AboutBlogActivity
import com.iamsdt.androidsketchpad.ui.DeveloperActivity
import com.iamsdt.androidsketchpad.ui.SplashScreen
import com.iamsdt.androidsketchpad.ui.bookmark.BookmarkActivity
import com.iamsdt.androidsketchpad.ui.details.DetailsActivity
import com.iamsdt.androidsketchpad.ui.main.MainActivity
import com.iamsdt.androidsketchpad.ui.page.PageActivity
import com.iamsdt.androidsketchpad.ui.page.PageDetailsActivity
import com.iamsdt.androidsketchpad.ui.search.SearchActivity
import com.iamsdt.androidsketchpad.ui.search.SearchDetailsActivity
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
    abstract fun detailsActivity(): DetailsActivity

    @ContributesAndroidInjector
    abstract fun bookmarkActivity(): BookmarkActivity


    @ContributesAndroidInjector
    abstract fun pageActivity(): PageActivity

    @ContributesAndroidInjector
    abstract fun pageDetailsActivity(): PageDetailsActivity

    @ContributesAndroidInjector
    abstract fun aboutBlogActivity(): AboutBlogActivity

    @ContributesAndroidInjector
    abstract fun searchActivity(): SearchActivity


    @ContributesAndroidInjector
    abstract fun searchDetailsActivity(): SearchDetailsActivity


    @ContributesAndroidInjector
    abstract fun developerActivity(): DeveloperActivity


    @ContributesAndroidInjector
    abstract fun aboutApp(): AboutApp


    @ContributesAndroidInjector
    abstract fun updateServices(): UpdateService

    @ContributesAndroidInjector
    abstract fun connectionReceiver(): ConnectivityChangeReceiver
}