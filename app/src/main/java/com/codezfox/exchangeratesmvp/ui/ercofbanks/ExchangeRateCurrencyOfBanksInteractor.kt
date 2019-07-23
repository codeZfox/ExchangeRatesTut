package com.codezfox.exchangeratesmvp.ui.ercofbanks

import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Single
import java.util.*


class ExchangeRateCurrencyOfBanksInteractor(
        private val repository: CurrencyRatesRepository,
        private val preferencesRepository: PreferencesRepository,
        private val database: DatabaseRepository
) {

    fun loadBanksRates(currency: Currency, sort: RateCurrencySort): Single<Triple<List<BankRate>, Date?, Boolean>> {
        return repository.getBanksRates(currency)
//                .map { it.data ?: emptyList() }
                .doOnSuccess {
                    database.saveBanksRates(it)
                    preferencesRepository.saveLastDateCurrency(currency, Date())
                }
                .map {
                    Triple<List<BankRate>, Date?, Boolean>(it.sort(sort), null, false)
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

    private fun List<BankRate>.sort(sort: RateCurrencySort): List<BankRate> {

        val comparator = if (sort == RateCurrencySort.BUY) {
            compareBy<BankRate> { it.sell }
        } else {
            compareByDescending { it.buy }
        }

        return this.sortedWith(comparator.then(compareBy { it.bank.bankId }))
    }

    fun sortBanksRates(list: List<BankRate>, sort: RateCurrencySort): List<BankRate> {
        return list.sort(sort)
    }

}