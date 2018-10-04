package com.codezfox.exchangeratesmvp.presentation.currencyrates

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.entity.CurrencyRate

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrencyRatesView : MvpView {

    fun showRates(items: List<CurrencyRate>)

}