package com.codezfox.exchangeratesmvp.domain.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
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
        val scale: Int // "4",

) : Serializable {

    companion object {

        private var mapFormats = hashMapOf<Int, String>()

        fun rateForUI(value: Double, scale: Int = 4): String {

            if (!mapFormats.containsKey(scale)) {
                mapFormats[scale] = "%.${scale}f"
            }

            return String.format(Locale.UK, mapFormats[scale]!!, value)
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