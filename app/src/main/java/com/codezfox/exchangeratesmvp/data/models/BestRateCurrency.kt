package com.codezfox.exchangeratesmvp.data.models

import android.arch.persistence.room.Embedded

data class BestRateCurrency(

        @Embedded
        var rate: BestRate,

        @Embedded
        var currency: Currency


) {
    fun getAmountString(): String? {
        return currency.getAmountString()
    }
}