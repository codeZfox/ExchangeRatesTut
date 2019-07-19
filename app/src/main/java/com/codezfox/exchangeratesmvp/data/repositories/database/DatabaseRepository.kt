package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Single

interface DatabaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveBestRates(rates: List<BestRate>)

    fun getBestRates(): Single<List<BestRateCurrency>>

    fun saveBanksRates(rates: List<RateBank>)

    fun getBanksRates(currency: Currency): Single<List<RateBank>>

    fun saveBranches(branches: List<Branch>)

    fun updateBranches(branches: List<BranchRate>)

    fun saveExchangeRates(branches: List<ExchangeRate>)

    fun getBranchCurrencyRates(branchId: String): Single<List<BranchCurrency2>>

    fun getBranchCurrencyRates(bankId: String, fromCurrency: String, toCurrency: String): Single<List<BranchCurrency>>

    fun getBankById(bankId: String): Single<Bank>

}