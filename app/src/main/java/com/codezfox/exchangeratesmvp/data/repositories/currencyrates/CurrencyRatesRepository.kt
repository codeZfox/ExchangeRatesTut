package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Single

interface CurrencyRatesRepository {

    fun getBestRates(): Single<BaseResponse<Rate>>

    fun getCurrencyRates(fromCurrency: Currency, toCurrency: Currency): Single<BaseResponse<BranchRate>>

    fun getCurrencies(): BaseResponse<Currency>

    fun getBanksRates(currency: Currency): Single<BaseResponse<RateBank>>

    fun getBankBranches(bank: Bank): Single<BaseResponse<Branch>>

}

