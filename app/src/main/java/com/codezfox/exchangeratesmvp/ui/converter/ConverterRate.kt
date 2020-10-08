package com.codezfox.exchangeratesmvp.ui.converter

import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem

data class ConverterRate(
    val bestRateCurrency: BestRateCurrency,
    val summary: String,
    val isSelected: Boolean
) : DisplayableItem {
    override fun areItemsTheSame(): String {
        return bestRateCurrency.currency.id
    }
}