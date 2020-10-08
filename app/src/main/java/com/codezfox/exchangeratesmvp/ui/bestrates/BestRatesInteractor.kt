package com.codezfox.exchangeratesmvp.ui.bestrates

import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class BestRatesInteractor(
    private val repository: CurrencyRatesRepository,
    private val database: DatabaseRepository,
    private val preferencesRepository: PreferencesRepository
) {

    private var currencies: List<Currency> = listOf()

    fun loadRates(): Completable {
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
            .ignoreElement()
            .subscribeOn(Schedulers.io())
    }

    fun getRates(): Flowable<Pair<List<BestRateCurrency>, Date?>> {
        return database.getBestRatesCurrencies()
            .map { list ->
                val date = preferencesRepository.getLastDateData()
                list to date
            }
    }

}