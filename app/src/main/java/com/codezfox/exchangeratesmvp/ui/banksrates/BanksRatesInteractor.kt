package com.codezfox.exchangeratesmvp.ui.banksrates

import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.RateBank
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Single
import java.util.*


class BanksRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val preferencesRepository: PreferencesRepository,
        private val database: DatabaseRepository
) {

    fun loadBanksRates(currency: Currency, sort: RateCurrencySort): Single<Triple<List<RateBank>, Date?, Boolean>> {
        return repository.getBanksRates(currency)
                .map { it.data ?: emptyList() }
                .doOnSuccess {
                    database.saveBanksRates(it)
                    preferencesRepository.saveLastDateCurrency(currency, Date())
                }
                .map {
                    Triple<List<RateBank>, Date?, Boolean>(it.sort(sort), null, false)
                }
                .onErrorResumeNext { exception ->
                    database.getBanksRates(currency).map { list ->
                        if (list.isEmpty()) {
                            throw exception
                        } else {
                            val date: Date? = preferencesRepository.getLastDateCurrency(currency)
                            Triple(list.sort(sort), date, true)
                        }
                    }
                }
    }

    private fun List<RateBank>.sort(sort: RateCurrencySort): List<RateBank> {
        return if (sort == RateCurrencySort.BUY) {
            this.sortedBy { it.sell }
        } else {
            this.sortedByDescending { it.buy }
        }
    }

    fun sortBanksRates(list: List<RateBank>, sort: RateCurrencySort): List<RateBank> {
        return list.sort(sort)
    }

}