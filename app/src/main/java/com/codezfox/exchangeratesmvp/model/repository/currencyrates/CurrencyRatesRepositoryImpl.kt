package com.codezfox.exchangeratesmvp.model.repository.currencyrates

import com.codezfox.exchangeratesmvp.entity.CurrencyRateResponse
import com.codezfox.exchangeratesmvp.extensions.bodyOrError
import com.codezfox.exchangeratesmvp.model.data.server.FinanceApi

class CurrencyRatesRepositoryImpl(
        val api: FinanceApi
) : CurrencyRatesRepository {


    override fun getCurrencyRates(): CurrencyRateResponse {
        return api.getRates("get_best_rates", "hiLlo77mAul94oINk19ANile", """[{"key":"params","value":"{\"api-version\":3, \"test_data\":0,\"ts\":\"0\", \"city_id\":15800}","type":"text","enabled":true,"description":""}]""").bodyOrError()
    }

}