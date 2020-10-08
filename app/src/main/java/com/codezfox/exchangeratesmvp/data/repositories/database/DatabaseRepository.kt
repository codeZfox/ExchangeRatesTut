package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Flowable

interface DatabaseRepository {

    fun saveCurrencies(currencies: List<Currency>)

    fun saveBestRates(rates: List<BestRate>)

    fun getBestRatesCurrencies(): Flowable<List<BestRateCurrency>>

    fun saveBanksRates(rates: List<BankRate>)

    fun getBanksRates(currency: Currency): Flowable<List<BankRate>>

    fun saveBranches(branches: List<Branch>)

    fun updateBranches(branches: List<RatesOfBranch>)

    fun saveExchangeRates(exchangeRates: List<ExchangeRate>, branches: List<RatesOfBranch>)

    fun getBranchCurrencyRates(branchId: String): Flowable<List<CurrencyExchangeRate>>

    fun saveExchangeRateBranch(branches: List<ExchangeRateBranch>)

    fun getBranchCurrencyRates(bankId: String, fromCurrency: String, toCurrency: String): Flowable<List<BranchWithExchangeRate>>

    fun getBankById(bankId: String): Flowable<Bank>

    fun insertService(list: List<Service>)

    fun deleteServices()

}