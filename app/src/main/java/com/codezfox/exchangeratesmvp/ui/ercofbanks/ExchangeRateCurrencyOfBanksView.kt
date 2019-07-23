package com.codezfox.exchangeratesmvp.ui.ercofbanks

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.paginator.screen.PaginatorView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface ExchangeRateCurrencyOfBanksView : PaginatorView<BankRate> {

    fun showCurrencyInfo(currency: Currency)

    fun showSortType(sort: RateCurrencySort)

    fun showLastDateUpdated(date: Date?)

}