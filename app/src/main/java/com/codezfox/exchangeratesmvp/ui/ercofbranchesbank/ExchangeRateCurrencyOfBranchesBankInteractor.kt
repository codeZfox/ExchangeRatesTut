package com.codezfox.exchangeratesmvp.ui.ercofbranchesbank

import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.BranchWithExchangeRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.ui.ercofbanks.RateCurrencySort
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*


class ExchangeRateCurrencyOfBranchesBankInteractor(
    private val repository: CurrencyRatesRepository,
    private val preferencesRepository: PreferencesRepository,
    private val database: DatabaseRepository
) {

    var places = listOf<Branch>()

    fun loadBanksRates(bank: Bank, fromCurrency: Currency, toCurrency: Currency): Completable {
        return if (places.isEmpty()) {
            repository.getBranches(bank)
                .doOnSuccess {
                    database.saveBranches(it)
                    places = it
                }
                .ignoreElement()
                .onErrorComplete()
        } else {
            Completable.complete()
        }
            .andThen(repository.getRatesOfBranch(fromCurrency, toCurrency)
                .doOnSuccess { list ->

                    database.saveExchangeRates(list.flatMap { it.exchangeRates }, list)

                    preferencesRepository.saveLastDateBank(bank, fromCurrency, toCurrency, Date())

                }
                .ignoreElement()
            )
            .subscribeOn(Schedulers.io())
    }

    fun getBanksRates(bank: Bank, fromCurrency: Currency, toCurrency: Currency, sort: RateCurrencySort): Flowable<Pair<List<BranchWithExchangeRate>, Date?>> {
        return database.getBranchCurrencyRates(bank.bankId, fromCurrency.id, toCurrency.id).map {
            it.sort(sort) to preferencesRepository.getLastDateBank(bank, fromCurrency, toCurrency)
        }
    }

    private fun List<BranchWithExchangeRate>.sort(sort: RateCurrencySort): List<BranchWithExchangeRate> {

        val comparator = if (sort == RateCurrencySort.BUY) {
            compareBy<BranchWithExchangeRate> { it.branchRate.sellRate }
        } else {
            compareByDescending { it.branchRate.buyRate }
        }

        return this.sortedWith(comparator.then(compareBy { it.branch.id }))
    }

}