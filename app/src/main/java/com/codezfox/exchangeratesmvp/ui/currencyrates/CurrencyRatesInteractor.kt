package com.codezfox.exchangeratesmvp.ui.currencyrates

import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import io.reactivex.Single
import java.util.*

class CurrencyRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    private var currencies: List<Currency> = listOf()

    fun loadRates(): Single<Triple<List<BestRateCurrency>, Date?, Boolean>> {

        return repository.getBestRates()
                .doOnSuccess {
                    database.saveBestRates(it)
                    preferencesRepository.saveLastDateData(Date())
                }
                .flatMap {
                    if (currencies.isEmpty()) {
                        repository.getCurrencies()
                    } else {
                        Single.just(emptyList())
                    }
                }
                .flatMap { currencies ->

                    if (currencies.isEmpty()) {
                        this.currencies = currencies
                        database.saveCurrencies(currencies)
                    }

                    database.getBestRates().map { Triple<List<BestRateCurrency>, Date?, Boolean>(it, null, false) }

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