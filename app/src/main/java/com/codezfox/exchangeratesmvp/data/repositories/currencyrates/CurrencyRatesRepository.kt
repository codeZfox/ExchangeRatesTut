package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.data.models.BaseResponse
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.Rate
import com.codezfox.exchangeratesmvp.data.models.RateBank
import io.reactivex.Single

interface CurrencyRatesRepository {

    fun getCurrencyRatesSingle(): Single<BaseResponse<Rate>>

    fun getCurrencies(): BaseResponse<Currency>

    fun getBanksRates(currency: Currency): BaseResponse<RateBank>
}

