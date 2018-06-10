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

        //demo 2018-06-08T23:02:00+06:00
        fun getReadableDate(string: String?):String{
            if (string == null || string.isEmpty()){
                return ""
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US)
            val output = SimpleDateFormat("EEE, MMM d, yy",Locale.US)
            val postDate = sdf.parse(string)

            return output.format(postDate)
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