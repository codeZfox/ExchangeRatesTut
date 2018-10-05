package com.codezfox.exchangeratesmvp.model.repository.currencyrates

import com.codezfox.exchangeratesmvp.entity.BaseResponse
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.CurrencyRate
import com.codezfox.exchangeratesmvp.extensions.bodyOrError
import com.codezfox.exchangeratesmvp.model.data.server.FinanceApi

class CurrencyRatesRepositoryImpl(

        private val api: FinanceApi

) : CurrencyRatesRepository {

    companion object {
        private const val GET_BEST_RATES = "get_best_rates"
        private const val GET_CURRENCIES = "get_currencies"
    }

    private fun getFields(action: String): Map<String, String> {
//        val fields = mutableMapOf<String, String>()
//        fields["action"] = action
//        fields["auth_key"] = "hiLlo77mAul94oINk19ANile"
//        fields["params"] = """[{"key":"params","value":"{\"api-version\":3, \"test_data\":0,\"ts\":\"0\", \"city_id\":15800}","type":"text","enabled":true,"description":""}]"""
        return mapOf(
                "action" to action,
                "auth_key" to "hiLlo77mAul94oINk19ANile",
                "params" to """[{"key":"params","value":"{\"api-version\":3, \"test_data\":0,\"ts\":\"0\", \"city_id\":15800}","type":"text","enabled":true,"description":""}]""")
    }

    override fun getCurrencyRates(): BaseResponse<CurrencyRate> {
        return api.getCurrencyRate(getFields(GET_BEST_RATES)).bodyOrError()
    }

    override fun getCurrencies(): BaseResponse<Currency> {
        return api.getCurrencies(getFields(GET_CURRENCIES)).bodyOrError()
    }

}