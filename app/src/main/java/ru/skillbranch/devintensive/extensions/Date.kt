package ru.skillbranch.devintensive.extensions

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.truncate

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))

    return  dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date{
    var time = this.time

    this.time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY

        else -> throw IllegalArgumentException("Unsupported unit name")
    }

    return this
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value:Int): String{

        var result:String=""
        var token:String=""
        var t1:String=""
        var t2:String=""
        var t3:String=""
        val patternperiods = Regex("""(\d+[2-90]+|[2-90]*)[234]""")

        when(this.toString()){
            "SECOND" -> {token = "секунд"; t1="у"; t2="ы";t3=""}
            "MINUTE" -> {token = "минут"; t1="у"; t2="ы";t3=""}
            "HOUR" -> {token = "час"; t1=""; t2="а";t3="ов"}
            "DAY" -> {token = "д"; t1="ень"; t2="ня";t3="ней"}
        }

        if(value == 1){
            result += token+t1
        }
        else if(patternperiods.matches(value.toString())){
            result += token+t2
        }
        else{
            result += token+t3
        }
        return "$value $result"
    }

}

fun Date.humanizeDiff(date:Date = Date()):String{
    var result:String

    ///  Time ranges
    val isNow = 1
    val wasWithinSec = 45
    val wasWithinMin = 75
    val wasNMins = 45*60
    val wasWithinHour = 75*60
    val wasNHours = 22*60*60
    val wasWithinDay = 26*60*60
    val wasNDays = 360*60*60

    ///////////////////

    /// Time tokens
    var tokSec = "секунд"
    var tokMin = "минут"
    var tokHour = "час"
    var tokDay = "дн"
    //////////////////


    val currDate = this
    val diffSec:Int = truncate(currDate.time/1000.0  - date.time/1000.0).toInt()
    var diffMin:Int = truncate(diffSec/60.0).toInt()
    val diffHour = truncate(diffMin/60.0).toInt()
    val diffDay = truncate(diffHour/24.0).toInt()

    val patternperiods = Regex("([234])|([2-5][0-9])")
    if(patternperiods.matches(diffMin.absoluteValue.toString())) tokMin += "ы"
    if(patternperiods.matches(diffHour.absoluteValue.toString())) tokHour += "а" else tokHour += "ов"
    if(patternperiods.matches(diffDay.absoluteValue.toString())) tokDay += "я" else tokDay += "ей"


    var postproc:Boolean = false
    when{
        diffSec.absoluteValue in 0..isNow -> result = "только что"
        diffSec.absoluteValue in (isNow+1)..wasWithinSec -> {result = "несколько $tokSec"; postproc = true}
        diffSec.absoluteValue in (wasWithinSec+1)..wasWithinMin -> result = "минуту"
        diffSec.absoluteValue in (wasWithinMin+1)..wasNMins -> {result = "${diffMin.absoluteValue} $tokMin"; postproc = true} // 75с - 45мин "N минут назад"
        diffSec.absoluteValue in (wasNMins+1)..wasWithinHour -> result = "час" // 45мин - 75мин "час назад"
        diffSec.absoluteValue in (wasWithinHour+1)..wasNHours -> {result = "${diffHour.absoluteValue} $tokHour"; postproc = true} // 75мин 22ч "N часов назад"
        diffSec.absoluteValue in (wasNHours+1)..wasWithinDay -> result = "день" // 22ч - 26ч "день назад"
        diffSec.absoluteValue in (wasWithinDay+1)..wasNDays -> {result = "${diffDay.absoluteValue} $tokDay"; postproc = true} // 26ч - 360д "N дней назад"
        diffSec < wasNDays -> result = "более года назад" // >360д "более года назад"
        diffSec > wasNDays -> result = "более чем через год" // >360д "более года назад"

        else -> result = "Был $diffDay $tokDay, $diffHour $tokHour, $diffMin $tokMin, $diffSec $tokSec назад"
    }
    if (postproc){
        when{
            diffSec > 0 -> result = "через $result"
            diffSec < 0 ->  result += " назад"
        }
    }


    return result
}




















