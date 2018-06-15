/*
 * Created by Shudipto Trafder
 * on 6/10/18 6:30 PM
 */

package com.iamsdt.androidsketchpad.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.iamsdt.androidsketchpad.R
import com.iamsdt.androidsketchpad.ui.main.MainActivity
import com.iamsdt.androidsketchpad.utils.ext.toNextActivity
import kotlinx.android.synthetic.main.activity_app_intro.*


class AppIntro : AppCompatActivity(),ViewPager.OnPageChangeListener {

    private lateinit var layouts: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setContentView(R.layout.activity_app_intro)

        layouts = intArrayOf(R.layout.welcome1,
                R.layout.welcome2,
                R.layout.welcome3,
                R.layout.welcome4,
                R.layout.welcome5)

        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()
        val myViewPagerAdapter = MyViewPagerAdapter()
        view_pager.adapter = myViewPagerAdapter
        view_pager.addOnPageChangeListener(this)

        btn_next.setOnClickListener({ _ ->
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts.size) {
                // move to next screen
                view_pager.currentItem = current
            } else {
                launchHomeScreen()
            }
        })
    }

    private fun getItem(i: Int): Int {
        return view_pager.currentItem + i
    }

    private fun launchHomeScreen() {
        toNextActivity(MainActivity::class)
        finish()
    }

    private fun addBottomDots(currentPage: Int) {
        val dots: Array<TextView?> = arrayOfNulls(layouts.size)

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            @Suppress("DEPRECATION")
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35f
            dots[i]?.setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots[i])
        }

        if (dots.isNotEmpty())
            dots[currentPage]?.setTextColor(colorsActive[currentPage])
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    @SuppressLint("SetTextI18n")
    override fun onPageSelected(position: Int) {
        addBottomDots(position)

        // changing the next button text 'NEXT' / 'GOT IT'
        if (position == layouts.size - 1) {
            // last page. make button text to GOT IT
            btn_next.text = "Start"
        } else {
            // still pages are left
            btn_next.text = "Next"
        }
    }

    inner class MyViewPagerAdapter : PagerAdapter() {

        private lateinit var layoutInflater: LayoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view: View = layoutInflater.inflate(layouts[position], container, false)

            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }
    }
}