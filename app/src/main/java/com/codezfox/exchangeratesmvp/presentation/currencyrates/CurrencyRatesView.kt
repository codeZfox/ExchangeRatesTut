package com.codezfox.exchangeratesmvp.presentation.currencyrates

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateCurrency
import com.codezfox.exchangeratesmvp.extensions.AddToEndSingleByTagStateStrategy
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrencyRatesView : MvpView {

    fun showRates(items: List<RateCurrency>)

    fun showShimmerEffect(show: Boolean)

    fun showProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun showEmptyText(text: String)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun hideEmptyText()

    fun showLastDateUpdated(date: Date?)


}