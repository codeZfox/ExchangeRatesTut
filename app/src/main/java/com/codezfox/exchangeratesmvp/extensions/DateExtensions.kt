package com.codezfox.exchangeratesmvp.extensions


import java.util.*


fun Date.compareDay(date: Date) = toCalendar().compareDay(date.toCalendar())

fun Date.compareDay(calendar: Calendar) = toCalendar().compareDay(calendar)

fun Calendar.compareDay(date: Date) = compareDay(date.toCalendar())

fun Calendar.compareDay(calendar: Calendar): Int {
    val compareYear = this.get(Calendar.YEAR).compareTo(calendar.get(Calendar.YEAR))

    if (compareYear == 0) {
        return this.get(Calendar.DAY_OF_YEAR).compareTo(calendar.get(Calendar.DAY_OF_YEAR))
    }

    return compareYear
}



fun Date.isToday() = toCalendar().compareWithToday() == 0

fun Date.compareWithToday() = toCalendar().compareWithToday()

fun Calendar.compareWithToday() = compareDay(Calendar.getInstance())



fun Date.compareWithTomorrow() = toCalendar().compareWithTomorrow()

fun Calendar.compareWithTomorrow(): Int {
    return compareDay(Calendar.getInstance().also {
        it.add(Calendar.DAY_OF_MONTH, 1)
    })
}



fun Date.compareWithYesterday() = toCalendar().compareWithYesterday()

fun Calendar.compareWithYesterday(): Int {
    return compareDay(Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, -1)
    })
}



fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().also {
        it.time = this
    }
}