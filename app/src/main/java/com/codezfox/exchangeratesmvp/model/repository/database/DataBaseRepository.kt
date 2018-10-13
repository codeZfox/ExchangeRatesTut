package com.codezfox.exchangeratesmvp.model.repository.database

import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateBank
import com.codezfox.exchangeratesmvp.entity.RateCurrency

interface DataBaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveRates(rates: List<Rate>)

    fun getBestRates(): List<RateCurrency>

    fun saveBanksRates(rates: List<RateBank>)

    fun getBanksRates(currency: Currency): List<RateBank>

}