package com.codezfox.exchangeratesmvp.extensions

import android.content.Context
import android.support.annotation.ColorInt


@ColorInt
fun Context.getDefaultThemeColor(attribute: Int): Int {
    val themeArray = getTheme().obtainStyledAttributes(intArrayOf(attribute))
    try {
        val index = 0
        val defaultColourValue = 0
        return themeArray.getColor(index, defaultColourValue)
    } finally {
        themeArray.recycle()
    }
}
