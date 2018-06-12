/*
 * Created by Shudipto Trafder
 * on 6/12/18 4:46 PM
 */

package com.iamsdt.androidsketchpad.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.iamsdt.androidsketchpad.R

abstract class SwipeUtil(dragDirs: Int,
                    swipeDirs: Int,
                    private val context: Context) :
        ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    var background: Drawable? = null
    var deleteIcon: Drawable? = null

    var xMarkMargin: Int = 0

    var initiated: Boolean = false

    var leftColorCode: Int = 0

    var leftSwipeLabel: String = ""

    private fun init() {
        background = ColorDrawable()
        xMarkMargin = context.resources.getDimension(R.dimen.ic_clear_margin).toInt()
        deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)
        deleteIcon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        initiated = true
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    abstract override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)


    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView
        if (!initiated) {
            init()
        }

        val itemHeight = itemView.bottom - itemView.top

        //Setting Swipe Background
        (background as ColorDrawable).color = leftColorCode
        background!!.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background!!.draw(c)

        val intrinsicWidth = deleteIcon!!.intrinsicWidth
        val intrinsicHeight = deleteIcon!!.intrinsicWidth

        val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
        val xMarkRight = itemView.right - xMarkMargin
        val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val xMarkBottom = xMarkTop + intrinsicHeight


        //Setting Swipe Icon
        deleteIcon!!.setBounds(xMarkLeft, xMarkTop + 16, xMarkRight, xMarkBottom)
        deleteIcon!!.draw(c)

        //Setting Swipe Text
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 48f
        paint.textAlign = Paint.Align.CENTER
        c.drawText(leftSwipeLabel, (xMarkLeft + 40).toFloat(), (xMarkTop + 10).toFloat(), paint)


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
