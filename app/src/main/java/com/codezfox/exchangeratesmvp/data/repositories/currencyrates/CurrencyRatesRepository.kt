package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.network.NBRate
import io.reactivex.Single

interface CurrencyRatesRepository {

    fun getBestRates(): Single<List<BestRate>>

    fun getCurrencies(): Single<List<Currency>>

    fun getNBBestRates(): Single<List<Pair<BestRate, NBRate>>>

    fun getNBCurrencies(): Single<List<Currency>>

    fun getBanksRates(currency: Currency): Single<List<BankRate>>

    fun getBranches(bank: Bank): Single<List<Branch>>

    fun getRatesOfBranch(fromCurrency: Currency, toCurrency: Currency): Single<List<RatesOfBranch>>

    fun getRatesOfBranch(branchId: String): Single<List<RatesOfBranch>>

    fun getServices(): Single<List<Service>>

}

