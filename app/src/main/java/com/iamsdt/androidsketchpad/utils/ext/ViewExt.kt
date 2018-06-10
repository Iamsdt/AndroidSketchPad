/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:16 AM
 */


package com.iamsdt.androidsketchpad.utils.ext

import android.support.design.widget.Snackbar
import android.view.View

fun View.gone(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, snackbarText, timeLength).show()
}