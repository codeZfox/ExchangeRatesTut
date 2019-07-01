package com.codezfox.exchangeratesmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.codezfox.exchangeratesmvp.data.models.Currency
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

}