package com.codezfox.exchangeratesmvp.data.models

import androidx.room.Embedded
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem

data class BestRateCurrency(

        @Embedded
        val rate: BestRate,

        @Embedded
        val currency: Currency

) : DisplayableItem {

    override fun areItemsTheSame(): String {
        return currency.id
    }

    fun getAmountString(): String? {
        return currency.getAmountString()
    }
}