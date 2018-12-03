package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.domain.models.BaseResponse
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.Rate
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.extensions.bodyOrError
import com.codezfox.exchangeratesmvp.data.server.FinanceApi
import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


class CurrencyRatesRepositoryImpl(

        private val api: FinanceApi,

        private val gson: Gson

) : CurrencyRatesRepository {

    companion object {
        private const val GET_CURRENCIES = "get_currencies"
        private const val GET_BEST_RATES = "get_best_rates"
        private const val GET_BANKS_RATES = "get_banks_rates"
    }

    private fun getFields(action: String, vararg params: Pair<String, String>): Map<String, String> {

        val paramsList: MutableList<Pair<String, String>> = params.toMutableList()
        paramsList.add("city_id" to "24248")

        return mapOf(
                "action" to action,
                "auth_key" to "hiLlo77mAul94oINk19ANile",
                "params" to gson.toJson(paramsList))
    }

    override fun getCurrencies(): BaseResponse<Currency> {
        val response = api.getInfo(getFields(GET_CURRENCIES)).bodyOrError()
        return parseResponse(response)
    }


    override fun getCurrencyRates(): BaseResponse<Rate> {
        val response = api.getInfo(getFields(GET_BEST_RATES)).bodyOrError()
        return parseResponse(response)
    }

    override fun getBanksRates(currency: Currency): BaseResponse<RateBank> {
        val response = api.getInfo(getFields(GET_BANKS_RATES, "currencyCode" to currency.id)).bodyOrError()
        return parseResponse(response)
    }

    private inline fun <reified T> parseResponse(error: JsonObject): T {
        return gson.fromJson(error, object : TypeToken<T>() {}.type)
    }

}