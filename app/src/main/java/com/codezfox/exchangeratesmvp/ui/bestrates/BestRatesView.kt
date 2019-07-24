package com.codezfox.exchangeratesmvp.ui.bestrates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.paginator.screen.PaginatorView
import java.util.*

interface BestRatesView : PaginatorView<BestRateCurrency> {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLastDateUpdated(date: Date?)

}