package com.codezfox.exchangeratesmvp.ui.banksrates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.RateBank
import com.codezfox.paginator.screen.PaginatorView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface BanksRatesView : PaginatorView<RateBank> {

    fun showCurrencyInfo(currency: Currency)

    fun showSortType(sort: RateCurrencySort)

    fun showLastDateUpdated(date: Date?)

}