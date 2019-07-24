package com.codezfox.exchangeratesmvp.ui.bestrates

import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import io.reactivex.Single
import java.util.*

class BestRatesInteractor(
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
                                .doOnSuccess { currencies ->
                                    this.currencies = currencies
                                    database.saveCurrencies(currencies)
                                }
                    } else {
                        Single.just(emptyList())
                    }
                }
                .flatMap {
                    database.getBestRatesCurrencies().map { Triple<List<BestRateCurrency>, Date?, Boolean>(it, null, false) }
                }.onErrorResumeNext { exception ->

                    val date = preferencesRepository.getLastDateData()

                    database.getBestRatesCurrencies().map { list ->
                        if (list.isEmpty()) {
                            throw exception
                        } else {
                            Triple(list, date, true)
                        }
                    }
                }

    }

}