package com.codezfox.exchangeratesmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui.bankbranch.BankBranchFragment
import com.codezfox.exchangeratesmvp.ui.ercofbranchesbank.ExchangeRateCurrencyOfBranchesBankFragment
import com.codezfox.exchangeratesmvp.ui.ercofbanks.ExchangeRateCurrencyOfBanksFragment
import com.codezfox.exchangeratesmvp.ui.bestrates.BestRatesFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class BestRates : SupportAppScreen() {
        override fun getFragment(): Fragment = BestRatesFragment()
    }

    class ExchangeRateCurrencyOfBanks(private val currency: Currency) : SupportAppScreen() {
        override fun getFragment(): Fragment = ExchangeRateCurrencyOfBanksFragment().also { fragment ->
            fragment.arguments = Bundle().also {
                it.putSerializable("Currency", currency)
            }
        }
    }

    class ExchangeRateCurrencyOfBranchesBank(private val bank: Bank, private val currency: Currency) : SupportAppScreen() {
        override fun getFragment(): Fragment = ExchangeRateCurrencyOfBranchesBankFragment().also { fragment ->
            fragment.arguments = Bundle().also {
                it.putSerializable("Bank", bank)
                it.putSerializable("Currency", currency)
            }
        }
    }

    class BankBranch(private val bank: Bank, private val branch: Branch) : SupportAppScreen() {
        override fun getFragment(): Fragment = BankBranchFragment().also { fragment ->
            fragment.arguments = Bundle().also {
                it.putSerializable("Bank", bank)
                it.putSerializable("Branch", branch)
            }
        }
    }

}