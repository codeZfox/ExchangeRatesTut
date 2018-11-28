package com.codezfox.exchangeratesmvp.domain.models

import android.arch.persistence.room.Embedded

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