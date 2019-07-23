package com.codezfox.exchangeratesmvp.ui.bankbranch

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate

@StateStrategyType(AddToEndSingleStrategy::class)
interface BankBranchView : MvpView {

    fun showTitle(bank: Bank?)

    fun showBranch(bank: Bank, branch: Branch, exchangeRates: List<CurrencyExchangeRate>)

}