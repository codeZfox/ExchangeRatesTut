package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Single

interface DatabaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveBestRates(rates: List<BestRate>)

    fun getBestRatesCurrencies(): Single<List<BestRateCurrency>>

    fun saveBanksRates(rates: List<BankRate>)

    fun getBanksRates(currency: Currency): Single<List<BankRate>>

    fun saveBranches(branches: List<Branch>)

    fun updateBranches(branches: List<RatesOfBranch>)

    fun saveExchangeRates(branches: List<ExchangeRate>)

    fun getBranchCurrencyRates(branchId: String): Single<List<CurrencyExchangeRate>>

    fun saveExchangeRateBranch(branches: List<ExchangeRateBranch>)

    fun getBranchCurrencyRates(bankId: String, fromCurrency: String, toCurrency: String): Single<List<BranchWithExchangeRate>>

    fun getBankById(bankId: String): Single<Bank>

    fun insertService(list: List<Service>)

    fun deleteServices()

}