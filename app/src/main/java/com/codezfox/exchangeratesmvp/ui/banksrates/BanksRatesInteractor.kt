package com.codezfox.exchangeratesmvp.ui.banksrates

import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.RateBank
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class BanksRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DatabaseRepository
) {

    fun loadBanksRates(currency: Currency, sort: RateCurrencySort): List<RateBank> {
        return try {
            repository.getBanksRates(currency).data!!.also {
                database.saveBanksRates(it)
            }
        } catch (e: Exception) {

            e.printStackTrace()

            if (e is SocketTimeoutException || e is UnknownHostException || e is ConnectException) {
                database.getBanksRates(currency).also {
                    if (it.isEmpty()) {
                        throw e
                    }
                }
            } else {
                throw e
            }
        }.sort(sort)

    }

    private fun List<RateBank>.sort(sort: RateCurrencySort): List<RateBank> {
        return if (sort == RateCurrencySort.BUY) {
            this.sortedBy { it.sell }
        } else {
            this.sortedByDescending { it.buy }
        }
    }

    fun sortBanksRates(list: List<RateBank>, sort: RateCurrencySort): List<RateBank> {
        return list.sort(sort)
    }

}