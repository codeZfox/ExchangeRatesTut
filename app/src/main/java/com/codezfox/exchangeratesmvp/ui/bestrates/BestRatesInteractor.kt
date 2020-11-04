package com.codezfox.exchangeratesmvp.ui.bestrates

import com.codezfox.exchangeratesmvp.data.config.Group
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

                    val bestRatesConfig = Group.BestRates()

                    list.map {
                        it.copy(
                                rate = it.rate.copy(
                                        buy_diff = if (bestRatesConfig.isBestRateDiff.value) it.rate.buy_diff else 0.0,
                                        sell_diff = if (bestRatesConfig.isBestRateDiff.value) it.rate.sell_diff else 0.0,
                                        nb_diff = if (bestRatesConfig.isBestRateDiffNb.value) it.rate.nb_diff else 0.0,
                                        bcse_diff = if (bestRatesConfig.isBestRateDiffBCSE.value) it.rate.bcse_diff else 0.0,
                                        nb_date = if (bestRatesConfig.isVisibleNbDate.value) it.rate.nb_date else null,
                                        bcse_date = if (bestRatesConfig.isVisibleBCSE.value) it.rate.bcse_date else null
                                )
                        )
                    }
                }
                .map { list ->
                    val date = preferencesRepository.getLastDateData()
                    list to date
                }
    }

}