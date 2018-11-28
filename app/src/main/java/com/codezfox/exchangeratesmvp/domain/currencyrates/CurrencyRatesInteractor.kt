package com.codezfox.exchangeratesmvp.domain.currencyrates

import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

class CurrencyRatesInteractor {

    @Inject
    lateinit var repository: CurrencyRatesRepository

    @Inject
    lateinit var database: DataBaseRepository

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    init {
        DaggerUtils.appComponent.inject(this)
    }

    fun loadCurrencyRates(): Pair<List<RateCurrency>, Date?> {
        var lastDateData: Date? = null

        try {

            val rates = repository.getCurrencyRates().data!!
            database.saveRates(rates)

            val currencies = repository.getCurrencies().data!!
            database.saveCurrencies(currencies)

            preferencesRepository.saveLastDateData(Date())

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is SocketTimeoutException || e is UnknownHostException || e is ConnectException) {
                lastDateData = preferencesRepository.getLastDateData()
            }
        }


        return database.getBestRates() to lastDateData
    }

}