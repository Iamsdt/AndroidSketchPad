package com.iamsdt.androidsketchpad.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.data.retrofit.RetInterface
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject lateinit var retrofit: Retrofit

    @Inject lateinit var picasso: Picasso

    @Inject
    lateinit var retInterface: RetInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Timber.i("$retrofit and picasso$picasso")

    }
}
