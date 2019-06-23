package com.codezfox.exchangeratesmvp.presentation.presenter.currencyrates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.presentation.paginator.screen.PaginatorView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrencyRatesView : PaginatorView<RateCurrency> {

    fun showLastDateUpdated(date: Date?)


}