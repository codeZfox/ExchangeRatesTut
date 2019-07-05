package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Single

interface DatabaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveRates(rates: List<Rate>)

    fun getBestRates(): Single<List<RateCurrency>>

    fun saveBanksRates(rates: List<RateBank>)

    fun getBanksRates(currency: Currency): Single<List<RateBank>>

    fun saveBranches(branches: List<Branch>)

    fun updateBranches(branches: List<BranchRate>)

    fun saveExchangeRates(branches: List<ExchangeRate>)

    fun getBranchCurrencyRates(bankId: String, fromCurrency: String, toCurrency: String): Single<List<BranchCurrency>>

}