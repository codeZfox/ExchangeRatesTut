package com.codezfox.exchangeratesmvp.entity

class CurrencyRateResponse {
    var exchangeRates: List<CurrencyRate>? = null
    var ts: Long = 0L
    var status: String? = null
}

class CurrencyRate(

        val currencyCode: String, // "USD",
        val buy: String, // "2.151000",
        val sell: String, // "2.165000",
        val nb: String, // "2.1508",
        val buy_diff: Float, // 0,
        val sell_diff: Float, // 0.002,
        val nb_diff: Float, // 0.0174,
        val nb_date: String, // "2018-10-05",
        val bcse_rate: String, // "2.1508",
        val bcse_date: String, // "2018-10-04",
        val bcse_diff: Float, // 0.0174,
        val nationalCurrencyId: String // "BYN"

) {
    override fun toString(): String {
        return "$currencyCode $nb"
    }
}