package com.codezfox.exchangeratesmvp.domain.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity

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