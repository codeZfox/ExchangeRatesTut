package com.codezfox.exchangeratesmvp.model.interactor.currencyrates

import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.entity.RateCurrency
import com.codezfox.exchangeratesmvp.model.repository.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.model.repository.database.DataBaseRepository
import com.codezfox.exchangeratesmvp.model.repository.preferences.PreferencesRepository
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


        return database.get() to lastDateData
    }


//    fun loadLocalCurrencyRates(): Pair<List<RateCurrency>, Date> {
//
//        val rates = repository.getCurrencyRates().data!!
//        database.saveRates(rates)
//
//        val currencies = repository.getCurrencies().data!!
//        database.saveCurrencies(currencies)
//
//
//        return database.get()
//    }


}