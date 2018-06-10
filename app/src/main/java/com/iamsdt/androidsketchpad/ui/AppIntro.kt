/*
 * Created by Shudipto Trafder
 * on 6/10/18 6:30 PM
 */

package com.iamsdt.androidsketchpad.ui

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.MessageButtonBehaviour
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.main.MainActivity

class AppIntro: MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.icon)
                .title("Android SketchPad")
                .description("Imagine Learn And Apply")
                .build())

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                .title("title 3")
                .description("Description 3")
                .build())


        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                .title("title 4")
                .description("Description 3")
                .build())

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                .title("title 5")
                .description("Description 3")
                .build(), MessageButtonBehaviour(View.OnClickListener {
            nextActivity()
        }, "Start"))
    }

    override fun onFinish() {
        super.onFinish()
        nextActivity()
    }

    private fun nextActivity() {
        startActivity(Intent(this,
                MainActivity::class.java))
        finish()
    }
}