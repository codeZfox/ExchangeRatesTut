package com.codezfox.exchangeratesmvp.model.repository.currencyrates

import com.codezfox.exchangeratesmvp.entity.BaseResponse
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.Rate

interface CurrencyRatesRepository {

    fun getCurrencyRates(): BaseResponse<Rate>

    fun getCurrencies(): BaseResponse<Currency>

}

