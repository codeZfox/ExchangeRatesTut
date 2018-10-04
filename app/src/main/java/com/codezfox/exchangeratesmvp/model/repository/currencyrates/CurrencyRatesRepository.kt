package com.codezfox.exchangeratesmvp.model.repository.currencyrates

import com.codezfox.exchangeratesmvp.entity.CurrencyRateResponse

interface CurrencyRatesRepository {

    fun getCurrencyRates(): CurrencyRateResponse

}

