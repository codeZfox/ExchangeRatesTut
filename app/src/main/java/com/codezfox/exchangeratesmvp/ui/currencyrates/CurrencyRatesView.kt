package com.codezfox.exchangeratesmvp.ui.currencyrates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.RateCurrency
import com.codezfox.paginator.screen.PaginatorView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrencyRatesView : PaginatorView<RateCurrency> {

    fun showLastDateUpdated(date: Date?)


}