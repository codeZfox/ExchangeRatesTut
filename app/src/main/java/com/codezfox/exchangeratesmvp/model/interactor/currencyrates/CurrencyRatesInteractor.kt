package com.codezfox.exchangeratesmvp.model.interactor.currencyrates

import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.entity.CurrencyRate
import com.codezfox.exchangeratesmvp.model.repository.currencyrates.CurrencyRatesRepository
import javax.inject.Inject

class CurrencyRatesInteractor {

    @Inject
    lateinit var repository: CurrencyRatesRepository

    init {
        DaggerUtils.appComponent.inject(this)
    }

    fun loadCurrencyRates(): List<CurrencyRate> {
        return repository.getCurrencyRates().exchangeRates!!
    }

}