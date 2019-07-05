package ru.skillbranch.devintensive.extensions

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

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
    return date.toString()
}


















