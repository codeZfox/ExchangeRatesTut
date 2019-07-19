package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Single

interface CurrencyRatesRepository {

    fun getBestRates(): Single<List<BestRate>>

    fun getCurrencyRates(fromCurrency: Currency, toCurrency: Currency): Single<BaseResponse<BranchRate>>

    fun getCurrencies(): Single<List<Currency>>

    fun getBanksRates(currency: Currency): Single<BaseResponse<RateBank>>

    fun getBankBranches(bank: Bank): Single<BaseResponse<Branch>>

    fun getBrancheExchangeRate(branchId: String): Single<BaseResponse<BranchRate>>

}

