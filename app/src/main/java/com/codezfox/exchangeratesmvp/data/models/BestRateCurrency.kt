package com.codezfox.exchangeratesmvp.data.models

import androidx.room.Embedded
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem

data class BestRateCurrency(

        @Embedded
        var rate: BestRate,

        @Embedded
        var currency: Currency

) : DisplayableItem {

    override fun areItemsTheSame(): String {
        return currency.id
    }

    fun getAmountString(): String? {
        return currency.getAmountString()
    }
}