package com.codezfox.exchangeratesmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui.branch.BranchFragment
import com.codezfox.exchangeratesmvp.ui.bankbranchesrates.BanksBranchesRatesFragment
import com.codezfox.exchangeratesmvp.ui.banksrates.BanksRatesFragment
import com.codezfox.exchangeratesmvp.ui.currencyrates.CurrencyRatesFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class BanksRates(private val currency: Currency) : SupportAppScreen() {
        override fun getFragment(): Fragment = BanksRatesFragment().also { fragment ->
            fragment.arguments = Bundle().also {
                it.putSerializable("Currency", currency)
            }
        }
    }

    class CurrencyRates : SupportAppScreen() {

        override fun getFragment(): Fragment = CurrencyRatesFragment()

    }

    class BankBranchesRates(private val bank: Bank, private val currency: Currency) : SupportAppScreen() {
        override fun getFragment(): Fragment = BanksBranchesRatesFragment().also { fragment ->
            fragment.arguments = Bundle().also {
                it.putSerializable("Bank", bank)
                it.putSerializable("Currency", currency)
            }
        }
    }

    class Branch(private val branch: com.codezfox.exchangeratesmvp.data.models.Branch) : SupportAppScreen() {
        override fun getFragment(): Fragment = BranchFragment().also { fragment ->
            fragment.arguments = Bundle().also {
                it.putSerializable("Branch", branch)
            }
        }
    }

}