package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        val last = value % 10
        var st = value.toString()
        st += when (this) {
            SECOND -> " секунд"
            MINUTE -> " минут"
            HOUR -> " часов"
            DAY -> " дней"
        }

        return when (last) {
            in 1..1 -> if (value in 11..11) st else st.replace("секунд", "секунду").replace(
                "минут",
                "минуту"
            ).replace("часов", "час").replace("дней","день")
            in 2..4 -> if (value in 12..14) st else st.replace("секунд", "секунды").replace(
                "минут",
                "минуты"
            ).replace("часов", "часа").replace("дней","дня")
            else -> st
        }
    }
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff: Long = (this.time - date.time)

    var rez: String = when (diff.absoluteValue / 1000) {
        in 0..1 -> if (diff > 0) "через мгновение" else "только что"
        in 1..45 -> if (diff > 0) "через несколько секунд" else "несколько секунд назад"
        in 45..75 -> if (diff > 0) "через минуту" else "минуту назад"
        in 75..45 * 60 -> if (diff > 0) "через ${diff.absoluteValue / 1000 / 60 % 60} минут" else "${diff.absoluteValue / 1000 / 60 % 60} минут назад"
        in 45 * 60..75 * 60 -> if (diff > 0) "через час" else "час назад"
        in 75 * 60..22 * 60 * 60 -> if (diff > 0) "через ${diff.absoluteValue / 1000 / 60 / 60 % 24} часов" else "${diff.absoluteValue / 1000 / 60 / 60 % 24} часов назад"
        in 22 * 60 * 60..26 * 60 * 60 -> if (diff > 0) "через день" else "день назад"
        in 26 * 60 * 60..360 * 60 * 60 * 24 -> if (diff > 0) "через ${diff.absoluteValue / 1000 / 60 / 60 / 24 % 360} дней" else "${diff.absoluteValue / 1000 / 60 / 60 / 24 % 360} дней назад"
        else -> if (diff > 0) "более чем через год" else "более года назад"
    }

    if (rez.contains("минут")) {
        val end = diff.absoluteValue / 1000 / 60 % 60 % 10
        rez = when (end) {
            in 1..1 -> if (diff.absoluteValue / 1000 / 60 % 60 in 11..11) rez else rez.replace("минут", "минуту")
            in 2..4 -> if (diff.absoluteValue / 1000 / 60 % 60 in 12..14) rez else rez.replace("минут", "минуты")
            else -> rez
        }
    }

    if (rez.contains("часов")) {
        rez = when (diff.absoluteValue / 1000 / 60 / 60 % 24) {
            in 2..4 -> rez.replace("часов", "часа")
            in 21..21 -> rez.replace("часов", "час")
            in 22..22 -> rez.replace("часов", "часа")
            else -> rez
        }
    }

    if (rez.contains("дней")) {
        val end = diff.absoluteValue / 1000 / 60 / 60 / 24 % 360 % 10
        rez = when (end) {
            in 1..1 -> if (diff.absoluteValue / 1000 / 60 / 60 / 24 % 360 in 11..11) rez else rez.replace(
                "дней",
                "день"
            )
            in 2..4 -> if (diff.absoluteValue / 1000 / 60 / 60 / 24 % 360 in 12..14) rez else rez.replace("дней", "дня")
            else -> rez
        }
    }

    return rez
}