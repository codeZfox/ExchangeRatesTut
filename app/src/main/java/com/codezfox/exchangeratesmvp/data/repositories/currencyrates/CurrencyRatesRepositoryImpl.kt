package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.extensions.bodyOrError
import com.codezfox.exchangeratesmvp.data.network.FinanceApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.Single


class CurrencyRatesRepositoryImpl(

        private val api: FinanceApi,

        private val gson: Gson

) : CurrencyRatesRepository {

    companion object {
        private const val GET_CURRENCIES = "get_currencies"
        private const val GET_BEST_RATES = "get_best_rates"
        private const val GET_BANKS_RATES = "get_banks_rates"
        private const val GET_PLACES = "get_places"
        private const val GET_CURRENCY_RATES = "get_currency_rates"
    }

    private fun getFields(action: String, vararg params: Pair<String, String>): Map<String, String> {

        val paramsList: MutableList<Pair<String, String>> = params.toMutableList()
        paramsList.add("city_id" to "15800")

        val paramsJson = paramsList.joinToString(",", "{", "}") { """"${it.first}":${it.second}""" }
        return mapOf(
                "action" to action,
                "auth_key" to "hiLlo77mAul94oINk19ANile",
                "params" to paramsJson)
    }

    override fun getCurrencies(): BaseResponse<Currency> {
        val response = api.getInfo(getFields(GET_CURRENCIES)).bodyOrError()
        return parseResponse(response)
    }

    override fun getBestRates(): Single<BaseResponse<Rate>> {
        return api.getInfoSingle(getFields(GET_BEST_RATES))
                .map { parseResponse<BaseResponse<Rate>>(it) }
    }

    override fun getBanksRates(currency: Currency): Single<BaseResponse<RateBank>> {
        return api.getInfoSingle(getFields(GET_BANKS_RATES, "currencyCode" to "\"${currency.id}\""))
                .map { parseResponse<BaseResponse<RateBank>>(it) }
    }

    override fun getBankBranches(bank: Bank): Single<BaseResponse<Branch>> {
        return api.getInfoSingle(getFields(GET_PLACES, "bank_id" to bank.bankId, "placeType" to "\"branch\""))
                .map { parseResponse<BaseResponse<Branch>>(it) }
    }

    override fun getCurrencyRates(fromCurrency: Currency, toCurrency: Currency): Single<BaseResponse<BranchRate>> {
        val fields = getFields(
                GET_CURRENCY_RATES,
                "fromCurrency" to "\"${fromCurrency.id}\"",
                "toCurrency" to "\"${toCurrency.id}\""
        )
        return api.getInfoSingle(fields)
                .map {
                    val response = parseResponse<BaseResponse<BranchRate>>(it)

                    response.data?.forEach { branchRate ->
                        branchRate.exchangeRates.forEach { exchangeRate ->
                            exchangeRate.branche_id = branchRate.branche_id
                        }
                    }

                    response
                }
    }

    private inline fun <reified T> parseResponse(error: JsonObject): T {
        return gson.fromJson(error, object : TypeToken<T>() {}.type)
    }

}