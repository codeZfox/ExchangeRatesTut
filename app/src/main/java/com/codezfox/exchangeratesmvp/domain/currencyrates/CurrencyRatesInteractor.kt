package com.codezfox.exchangeratesmvp.domain.currencyrates

import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class CurrencyRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DataBaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    fun loadCurrencyRates(): Pair<List<RateCurrency>, Date?> {
        return try {

            val rates = repository.getCurrencyRates().data!!
            database.saveRates(rates)

            val currencies = repository.getCurrencies().data!!
            database.saveCurrencies(currencies)

            preferencesRepository.saveLastDateData(Date())

            database.getBestRates() to null

        } catch (e: Exception) {
            e.printStackTrace()

            if (e is SocketTimeoutException || e is UnknownHostException || e is ConnectException) {

                database.getBestRates().also {
                    if (it.isEmpty()) {
                        throw e
                    }
                } to preferencesRepository.getLastDateData()

            } else {
                throw e
            }
        }

    }

}