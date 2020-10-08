package com.codezfox.exchangeratesmvp.ui.ercofbanks

import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*


class ExchangeRateCurrencyOfBanksInteractor(
    private val repository: CurrencyRatesRepository,
    private val preferencesRepository: PreferencesRepository,
    private val database: DatabaseRepository
) {

    fun loadBanksRates(currency: Currency): Completable {
        return repository.getBanksRates(currency)
            .doOnSuccess {
                database.saveBanksRates(it)
                preferencesRepository.saveLastDateCurrency(currency, Date())
            }
            .ignoreElement()
            .subscribeOn(Schedulers.io())
    }

    fun getBanksRates(currency: Currency, sort: RateCurrencySort): Flowable<Pair<List<BankRate>, Date?>> {
        return database.getBanksRates(currency)
            .map { list ->
                val date: Date? = preferencesRepository.getLastDateCurrency(currency)
                list.sort(sort) to date
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