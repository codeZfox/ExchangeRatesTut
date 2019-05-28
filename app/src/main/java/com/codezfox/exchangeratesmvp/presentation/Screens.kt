package com.codezfox.exchangeratesmvp.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.presentation.view.banksrates.BanksRatesFragment
import com.codezfox.exchangeratesmvp.presentation.view.currencyrates.CurrencyRatesFragment
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

}