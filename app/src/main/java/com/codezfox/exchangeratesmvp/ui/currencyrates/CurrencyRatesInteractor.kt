package com.codezfox.exchangeratesmvp.ui.currencyrates

import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.RateCurrency
import io.reactivex.Single
import java.util.*

class CurrencyRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    private var currencies: List<Currency> = listOf()

    fun loadRates(): Single<Triple<List<RateCurrency>, Date?, Boolean>> {

        return repository.getCurrencyRatesSingle()
                .map { it.data }
                .flatMap { data ->

                    database.saveRates(data)

                    if (currencies.isEmpty()) {
                        currencies = repository.getCurrencies().data!!
                        database.saveCurrencies(currencies)
                    }

                    val date = Date()

                    preferencesRepository.saveLastDateData(date)

                    database.getBestRates().map { Triple<List<RateCurrency>, Date?, Boolean>(it, null, false) }
//                }
                }.onErrorResumeNext { exception ->

                    val date = preferencesRepository.getLastDateData()

                    database.getBestRates().map { list ->
                        if (list.isEmpty()) {
                            throw exception
                        } else {
                            Triple(list, date, true)
                        }
                    }
                }

    }

}