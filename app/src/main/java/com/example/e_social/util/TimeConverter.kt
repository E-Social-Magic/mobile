package com.example.e_social.util

import android.os.Build
import androidx.annotation.RequiresApi
import org.ocpsoft.prettytime.PrettyTime
import java.time.ZonedDateTime
import java.util.*


object TimeConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun converter(time: String): String {
        val d: ZonedDateTime = ZonedDateTime.parse(time)
        val prettyTime = PrettyTime(Locale.getDefault())
        return prettyTime.format(d)
    }
}