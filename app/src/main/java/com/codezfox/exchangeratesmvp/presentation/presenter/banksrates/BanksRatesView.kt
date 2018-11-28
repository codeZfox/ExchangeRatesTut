package com.codezfox.exchangeratesmvp.presentation.presenter.banksrates

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.extensions.AddToEndSingleByTagStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface BanksRatesView : MvpView {

    fun showRates(items: List<RateBank>)

    fun showShimmerEffect(show: Boolean)

    fun showProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun showEmptyText(text: String)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun hideEmptyText()

    fun showCurrencyInfo(currency: Currency)


}