package com.codezfox.exchangeratesmvp.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class BaseResponse<T> {
    @SerializedName("exchangeRates", alternate = ["currencies"])
    var data: List<T>? = null
    var ts: Long = 0L
    var status: String? = null
}

data class Bank(

        @SerializedName("bank_id")
        val bankId: String, //"43"
        val name: String, //"Технобанк"
        val isOpened: String //"1"
)

@Entity(primaryKeys = ["fromCurrency", "bankId"])
data class RateBank(

        @Embedded
        val bank: Bank,

        val buy: Double, //"2.120000",
        val sell: Double, //"2.132000",
        val updateTime: Long, //1539440743,
        val nationalCurrencyId: String, //"BYN",
        val fromCurrency: String //"USD"

)

@Entity
data class Rate(

        @PrimaryKey
        val currencyCode: String, // "USD",
        val buy: Double, // "2.151000",
        val sell: Double, // "2.165000",
        val nb: Double, // "2.1508",
        val buy_diff: Double, // 0,
        val sell_diff: Double, // 0.002,
        val nb_diff: Double, // 0.0174,
        val nb_date: Date?, // "2018-10-05",
        val bcse_rate: Double, // "2.1508",
        val bcse_date: Date?, // "2018-10-04",
        val bcse_diff: Double, // 0.0174,
        val nationalCurrencyId: String // "BYN"

)

data class RateCurrency(

        @Embedded
        var rate: Rate,

        @Embedded
        var currency: Currency


) {
    fun getAmountString(): String? {
        return "${currency.amount} ${currency.plural_short}"
    }
}


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