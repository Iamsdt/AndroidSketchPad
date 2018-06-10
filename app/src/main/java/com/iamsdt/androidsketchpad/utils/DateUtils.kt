/*
 * Created by Shudipto Trafder
 * on 6/9/18 11:10 AM
 */

/*
 * Developed by Shudipto Trafder
 * at  6/7/18 4:36 PM.
 */

package com.iamsdt.androidsketchpad.utils

import org.joda.time.DateTime
import org.joda.time.Days
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DateUtils{

    companion object {
        fun getTodayDate():String{
            //look like wed, may 7
            val sp = SimpleDateFormat("EEE, MMM d",Locale.getDefault())
            sp.format(Date())
            return sp.format(Date())
        }

        fun getCurrentTime():String{
            //look like 12:30 pm
            val sp = SimpleDateFormat("h:mm a",Locale.getDefault())
            sp.format(Date())
            return sp.format(Date())
        }

        fun currentDate():Long = Date().time

        fun compareTwoDate(first: Date, second: Date): Date
                = if (first.before(second)) {
            first
        } else {
            second
        }

        fun compareDateIntervals(oldDate:Long):Int{

            val today = DateTime(Date())
            val preDate = DateTime(oldDate)

            val day = Days.daysBetween(preDate,today).days
            Timber.i(day.toString())

            return day
        }
    }

}