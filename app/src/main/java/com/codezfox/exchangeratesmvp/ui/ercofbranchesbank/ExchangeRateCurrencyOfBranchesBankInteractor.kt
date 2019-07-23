package com.codezfox.exchangeratesmvp.ui.ercofbranchesbank

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.ui.ercofbanks.RateCurrencySort
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*


class ExchangeRateCurrencyOfBranchesBankInteractor(
        private val repository: CurrencyRatesRepository,
        private val preferencesRepository: PreferencesRepository,
        private val database: DatabaseRepository
) {

    var places = listOf<Branch>()

    fun loadBanksRates(bank: Bank, fromCurrency: Currency, toCurrency: Currency, sort: RateCurrencySort): Single<Triple<List<BranchExchangeRate>, Date?, Boolean>> {

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
        }.andThen(repository.getRatesOfBranch(fromCurrency, toCurrency)
                .flatMap { list ->

                    database.saveExchangeRates(list.flatMap { it.exchangeRates })
                    database.updateBranches(list)

                    preferencesRepository.saveLastDateBank(bank, fromCurrency, toCurrency, Date())

                    database.getBranchCurrencyRates(bank.bankId, fromCurrency.id, toCurrency.id).map { Triple<List<BranchExchangeRate>, Date?, Boolean>(it.sort(sort), null, false) }
                }
                .onErrorResumeNext { exception ->
                    database.getBranchCurrencyRates(bank.bankId, fromCurrency.id, toCurrency.id).map { list ->
                        if (list.isEmpty()) {
                            throw exception
                        } else {
                            val date = preferencesRepository.getLastDateBank(bank, fromCurrency, toCurrency)
                            Triple(list.sort(sort), date, true)
                        }
                    }
                })
    }

    private fun List<BranchExchangeRate>.sort(sort: RateCurrencySort): List<BranchExchangeRate> {

        val comparator = if (sort == RateCurrencySort.BUY) {
            compareBy<BranchExchangeRate> { it.branchRate.sellRate }
        } else {
            compareByDescending { it.branchRate.buyRate }
        }

        return this.sortedWith(comparator.then(compareBy { it.branch.id }))
    }

    fun sortBanksRates(list: List<BranchExchangeRate>, sort: RateCurrencySort): List<BranchExchangeRate> {
        return list.sort(sort)
    }

}