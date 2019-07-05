package ru.skillbranch.devintensive.extensions

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.truncate

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String="dd.MM.yyyy HH:mm:ss"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))

    return  dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnit): Date{
    var time = this.time

    this.time += when(units){
        TimeUnit.SECOND -> value * SECOND
        TimeUnit.MINUTE -> value * MINUTE
        TimeUnit.HOUR -> value * HOUR
        TimeUnit.DAY -> value * DAY

        else -> throw IllegalArgumentException("Unsupported unit name")
    }

    return this
}

enum class TimeUnit{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.humanizeDiff(date:Date = Date()):String{
    val result:String

    ///  Time ranges
    val isNow = 10
    val wasWithinSec = 60
    val wasWithinMin = 3600
    ///////////////////

    /// Time tokens
    var tokSec = "секунд"
    var tokMin = "минут"
    var tokHour = "час"
    var tokDay = "д*н"
    //////////////////


    val currDate = Date()
    val diffSec:Int = truncate(currDate.time/1000.0  - date.time/1000.0).toInt()
    val diffMin:Int = truncate(diffSec/60.0).toInt()
    val diffHour = truncate(diffMin/60.0).toInt()
    val diffDay = truncate(diffHour/24.0).toInt()

    when(diffSec){
        1 -> tokSec += "у"
        2,3,4 -> tokSec += "ы"
    }


    when{
        diffSec in 0..isNow -> result = "Сейчас онлайн"
        diffSec in isNow..wasWithinSec -> result = "Был $diffSec $tokSec назад"
        diffSec in wasWithinSec..wasWithinMin -> result = "Был $diffMin $tokMin назад"

        else -> result = "Был $diffDay $tokDay, $diffHour $tokHour, $diffMin $tokMin, $diffSec $tokSec назад"
    }

    return result
}


















