package com.codezfox.exchangeratesmvp.domain

import com.codezfox.exchangeratesmvp.domain.models.BaseResponse
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.Rate
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import io.reactivex.Single

interface CurrencyRatesRepository {

    fun getCurrencyRatesSingle(): Single<BaseResponse<Rate>>

    fun getCurrencies(): BaseResponse<Currency>

    fun getBanksRates(currency: Currency): BaseResponse<RateBank>
}

