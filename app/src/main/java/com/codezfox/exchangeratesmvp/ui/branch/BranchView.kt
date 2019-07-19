package com.codezfox.exchangeratesmvp.ui.branch

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.BranchCurrency2

@StateStrategyType(AddToEndSingleStrategy::class)
interface BranchView : MvpView {

    fun showBranch(bank: Bank, branch: Branch, exchangeRates: List<BranchCurrency2>)

}