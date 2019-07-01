package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.Rate
import com.codezfox.exchangeratesmvp.data.models.RateBank
import com.codezfox.exchangeratesmvp.data.models.RateCurrency
import io.reactivex.Single

interface DatabaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveRates(rates: List<Rate>)

    fun getBestRates(): Single<List<RateCurrency>>

    fun saveBanksRates(rates: List<RateBank>)

    fun getBanksRates(currency: Currency): List<RateBank>

}