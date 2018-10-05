package com.codezfox.exchangeratesmvp.model.repository.currencyrates

import com.codezfox.exchangeratesmvp.entity.BaseResponse
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.CurrencyRate

interface CurrencyRatesRepository {

    fun getCurrencyRates(): BaseResponse<CurrencyRate>

    fun getCurrencies(): BaseResponse<Currency>

}

