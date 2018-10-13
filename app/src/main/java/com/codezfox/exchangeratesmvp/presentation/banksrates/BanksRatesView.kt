package com.codezfox.exchangeratesmvp.presentation.banksrates

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.entity.RateBank
import com.codezfox.exchangeratesmvp.extensions.AddToEndSingleByTagStateStrategy
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface BanksRatesView : MvpView {

    fun showRates(items: List<RateBank>)

    fun showShimmerEffect(show: Boolean)

    fun showProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun showEmptyText(text: String)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "EmptyText")
    fun hideEmptyText()


}