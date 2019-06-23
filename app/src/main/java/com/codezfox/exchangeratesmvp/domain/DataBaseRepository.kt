package com.codezfox.exchangeratesmvp.domain

import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.Rate
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import io.reactivex.Single

interface DataBaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveRates(rates: List<Rate>)

    fun getBestRates(): Single<List<RateCurrency>>

    fun saveBanksRates(rates: List<RateBank>)

    fun getBanksRates(currency: Currency): List<RateBank>

}