package com.codezfox.exchangeratesmvp.presentation.currencyrates

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.entity.CurrencyRate
import com.codezfox.exchangeratesmvp.extensions.AddToEndSingleByTagStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrencyRatesView : MvpView {

    fun showRates(items: List<CurrencyRate>)

    fun showShimmerEffect(show: Boolean)

    fun showProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun showEmptyText(text: String)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun hideEmptyText()


}