package com.codezfox.exchangeratesmvp.domain.currencyrates

import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.*

class CurrencyRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DataBaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    val subjectDate: Subject<Optional<Date>> = BehaviorSubject.create()

    fun loadRates(): Single<List<RateCurrency>> {

        return repository.getCurrencyRatesSingle()
                .map { it.data }
                .flatMap { data ->

                    database.saveRates(data)

                    val currencies = repository.getCurrencies().data!!
                    database.saveCurrencies(currencies)

                    val date = Date()

                    subjectDate.onNext(Optional.of(date))

                    preferencesRepository.saveLastDateData(date)

                    database.getBestRates()

                }.onErrorResumeNext { exception ->

                    val data = preferencesRepository.getLastDateData()

                    subjectDate.onNext(Optional.ofNullable(data))

                    database.getBestRates().map { list ->
                        if (list.isEmpty()) {
                            throw exception
                        } else {
                            list
                        }
                    }
                }

    }

}