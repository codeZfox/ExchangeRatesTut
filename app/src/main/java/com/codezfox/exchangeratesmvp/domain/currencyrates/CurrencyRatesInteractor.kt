package com.codezfox.exchangeratesmvp.domain.currencyrates

import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.*

class CurrencyRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DataBaseRepository,
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