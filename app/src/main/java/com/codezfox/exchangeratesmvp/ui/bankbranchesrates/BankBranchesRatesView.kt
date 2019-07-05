package com.codezfox.exchangeratesmvp.ui.bankbranchesrates

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.BranchCurrency
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui.banksrates.RateCurrencySort
import com.codezfox.paginator.screen.PaginatorView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface BankBranchesRatesView : PaginatorView<BranchCurrency> {

    fun showTitle(title: String, subTitle: String)

    fun showSortType(sort: RateCurrencySort)

    fun showLastDateUpdated(date: Date?)

}