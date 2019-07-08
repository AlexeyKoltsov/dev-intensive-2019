package ru.skillbranch.devintensive.extensions


import java.text.SimpleDateFormat
import java.util.*
import kotlin.IllegalArgumentException
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

        val patternperiods1 = Regex("""(\d+[2-90]+|[2-90]*)[1]""")
        val patternperiods2 = Regex("""(\d+[2-90]+|[2-90]*)[234]""")

        when(this.toString()){
            "SECOND" -> {token = "секунд"; t1="у"; t2="ы";t3=""}
            "MINUTE" -> {token = "минут"; t1="у"; t2="ы";t3=""}
            "HOUR" -> {token = "час"; t1=""; t2="а";t3="ов"}
            "DAY" -> {token = "д"; t1="ень"; t2="ня";t3="ней"}
        }

        if(patternperiods1.matches(value.toString())){
            result += token+t1
        }
        else if(patternperiods2.matches(value.toString())){
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
    val wasNDays = 360*24*60*60


    val currDate = this
    val diffSec:Int = truncate(currDate.time/1000.0  - date.time/1000.0).toInt()
    var diffMin:Int = truncate(diffSec/60.0).toInt()
    val diffHour = truncate(diffMin/60.0).toInt()
    val diffDay = truncate(diffHour/24.0).toInt()


    var postproc:Boolean = true
    when{
        diffSec.absoluteValue in 0..isNow -> {result = "только что"; postproc = false}
        diffSec.absoluteValue in (isNow+1)..wasWithinSec -> result = "несколько секунд"
        diffSec.absoluteValue in (wasWithinSec+1)..wasWithinMin -> result = "минуту"
        diffSec.absoluteValue in (wasWithinMin+1)..wasNMins -> result = "${TimeUnits.MINUTE.plural(diffMin.absoluteValue)}"
        diffSec.absoluteValue in (wasNMins+1)..wasWithinHour -> result = "час"
        diffSec.absoluteValue in (wasWithinHour+1)..wasNHours -> result = "${TimeUnits.HOUR.plural(diffHour.absoluteValue)}"
        diffSec.absoluteValue in (wasNHours+1)..wasWithinDay -> result = "день"
        diffSec.absoluteValue in (wasWithinDay+1)..wasNDays -> result = "${TimeUnits.DAY.plural(diffDay.absoluteValue)}"
        diffSec < -wasNDays -> {result = "более года назад"; postproc = false}
        diffSec > wasNDays -> {result = "более чем через год"; postproc = false}

        else -> throw IllegalArgumentException("Unsupported time range. diffSec: $diffSec")
    }
    if (postproc){
        when{
            diffSec > 0 -> result = "через $result"
            diffSec < 0 ->  result += " назад"
        }
    }
    return result
}




















