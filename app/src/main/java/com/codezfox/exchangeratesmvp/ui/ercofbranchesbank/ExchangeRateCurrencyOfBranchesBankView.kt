package com.codezfox.exchangeratesmvp.ui.ercofbranchesbank

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.BranchWithExchangeRate
import com.codezfox.exchangeratesmvp.ui.ercofbanks.RateCurrencySort
import com.codezfox.paginator.screen.PaginatorView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface ExchangeRateCurrencyOfBranchesBankView : PaginatorView<BranchWithExchangeRate> {

    fun showTitle(title: String, subTitle: String)

    fun showSortType(sort: RateCurrencySort)

    fun showLastDateUpdated(date: Date?)

}