package com.codezfox.exchangeratesmvp.data.repositories.currencyrates

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.network.FinanceApi
import com.codezfox.exchangeratesmvp.data.network.NBRate
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
        private const val GET_SERVICES = "get_services"
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

    override fun getCurrencies(): Single<List<Currency>> {
        return api.getInfoSingle(getFields(GET_CURRENCIES))
                .map { parseResponse<BaseResponse<Currency>>(it).data }
    }

    override fun getBestRates(): Single<List<BestRate>> {
        return api.getInfoSingle(getFields(GET_BEST_RATES))
                .map { parseResponse<BaseResponse<BestRate>>(it).data }
    }

    override fun getNBCurrencies(): Single<List<Currency>> {
        return api.getCurrencies("https://www.nbrb.by/api/exrates/currencies")
            .map { list ->
                list.filter { CurrencyType.valueOfOrNull(it.curAbbreviation) != null }
                    .sortedBy { CurrencyType.valueOfOrNull(it.curAbbreviation) }
                    .mapIndexed { index, it ->
                        Currency(
                            it.curAbbreviation,
                            it.curName,
                            it.curName,
                            it.curName,
                            it.curAbbreviation,
                            "",
                            "",
                            "1",
                            "1",
                            4,
                            index
                        )
                    }
            }
    }

    override fun getNBBestRates(): Single<List<Pair<BestRate, NBRate>>> {
        return api.getNBBestRates("https://www.nbrb.by/api/exrates/rates?periodicity=0")
            .map {
                it.map {
                    BestRate(it.curAbbreviation, it.curOfficialRate,it.curOfficialRate,it.curOfficialRate,0.0,0.0,0.0, it.date, it.curOfficialRate, null, 0.0, "BYN") to it                    }
            }
    }

    override fun getBanksRates(currency: Currency): Single<List<BankRate>> {
        return api.getInfoSingle(getFields(GET_BANKS_RATES, "currencyCode" to "\"${currency.id}\""))
                .map { parseResponse<BaseResponse<BankRate>>(it).data }
    }

    override fun getBranches(bank: Bank): Single<List<Branch>> {
        return api.getInfoSingle(getFields(GET_PLACES, "bank_id" to bank.bankId, "placeType" to "\"branch\""))
                .map { parseResponse<BaseResponse<Branch>>(it).data }
    }

    override fun getRatesOfBranch(branchId: String): Single<List<RatesOfBranch>> {
        return getCurrencyRates(getFields(GET_CURRENCY_RATES, "places" to "[$branchId]"))
    }

    override fun getRatesOfBranch(fromCurrency: Currency, toCurrency: Currency): Single<List<RatesOfBranch>> {
        val fields = getFields(GET_CURRENCY_RATES,
                "fromCurrency" to "\"${fromCurrency.id}\"",
                "toCurrency" to "\"${toCurrency.id}\""
        )
        return getCurrencyRates(fields)
    }

    private fun getCurrencyRates(map: Map<String, String>): Single<List<RatesOfBranch>> {
        return api.getInfoSingle(map)
                .map {
                    parseResponse<BaseResponse<RatesOfBranch>>(it).data
                            ?.onEach { branchRate ->
                                branchRate.exchangeRates.forEach { exchangeRate ->
                                    exchangeRate.branche_id = branchRate.branche_id
                                }
                            }
                }
    }

    override fun getServices(): Single<List<Service>> {
        return api.getInfoSingle(getFields(GET_SERVICES))
                .map { parseResponse<BaseResponse<Service>>(it).data }
    }

    private inline fun <reified T> parseResponse(jsonObject: JsonObject): T {
        return gson.fromJson(jsonObject, object : TypeToken<T>() {}.type)
    }

}