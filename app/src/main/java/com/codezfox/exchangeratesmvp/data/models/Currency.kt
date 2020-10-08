package com.codezfox.exchangeratesmvp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

@Entity
data class Currency(

        @PrimaryKey
        val id: String, // "USD",
        val name: String, // "Доллар США",
        val plural: String, // "доллар США",
        val plural_short: String, // "доллар",
        val code: String, // "USD",
        val measureUnit: String, // "",
        val flag: String, // "http://img.tyt.by/i/icons/android/new/USD.png",
        val amount: String, // "1",
        val status: String, // "1",
        val scale: Int, // "4",
        val order: Int

) : Serializable {

    fun getAmountString(): String? {
        return "$amount $plural_short"
    }

    companion object {

        private var mapFormats = hashMapOf<Int, String>()

        fun rateForUI(value: Double, scale: Int = 4, fillZero: Boolean = true): String {

            val s = if (fillZero) "0" else "#"
            val decimalFormat = DecimalFormat("###,###.${s.repeat(scale)}", DecimalFormatSymbols(Locale.UK).also {
                it.setGroupingSeparator(' ')
            })
            if (!mapFormats.containsKey(scale)) {
                mapFormats[scale] = "%.${scale}f"
            }
            //todo
            return decimalFormat.format(value)
        }


        fun rateDiffForUI(value: Double, scale: Int = 4): String {

            if (!mapFormats.containsKey(scale)) {
                mapFormats[scale] = "%.${scale}f"
            }

            val string = String.format(Locale.UK, mapFormats[scale]!!, value)
            if (value > 0) {
                return "+$string"
            }
            return string
        }
    }
}