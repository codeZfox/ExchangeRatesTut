package com.codezfox.exchangeratesmvp.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class BestRate(

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


