package com.codezfox.exchangeratesmvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui.bankbranch.BankBranchFragment
import com.codezfox.exchangeratesmvp.ui.bestrates.BestRatesFragment
import com.codezfox.exchangeratesmvp.ui.converter.ConverterFragment
import com.codezfox.exchangeratesmvp.ui.ercofbanks.ExchangeRateCurrencyOfBanksFragment
import com.codezfox.exchangeratesmvp.ui.ercofbranchesbank.ExchangeRateCurrencyOfBranchesBankFragment
import com.codezfox.exchangeratesmvp.ui.main.MainFragment
import com.codezfox.exchangeratesmvp.ui.settings.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class Main : SupportAppScreen() {
        override fun getFragment(): Fragment = MainFragment()
    }

    class BestRates() : SupportAppScreen() {
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

    class Converter : SupportAppScreen() {
        override fun getFragment(): Fragment = ConverterFragment()
    }

    class Settings : SupportAppScreen() {
        override fun getFragment(): Fragment = SettingsFragment()
    }

}