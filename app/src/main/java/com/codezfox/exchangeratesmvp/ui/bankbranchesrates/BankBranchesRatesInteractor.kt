package com.codezfox.exchangeratesmvp.ui.bankbranchesrates

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.ui.banksrates.RateCurrencySort
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*


class BankBranchesRatesInteractor(
        private val repository: CurrencyRatesRepository,
        private val preferencesRepository: PreferencesRepository,
        private val database: DatabaseRepository
) {

    var places = listOf<Branch>()

    fun loadBanksRates(bank: Bank, fromCurrency: Currency, toCurrency: Currency, sort: RateCurrencySort): Single<Triple<List<BranchCurrency>, Date?, Boolean>> {

        return if (places.isEmpty()) {
            repository.getBankBranches(bank)
                    .map {
                        it.data!!
                    }
                    .doOnSuccess {
                        database.saveBranches(it)
                        places = it
                    }
                    .ignoreElement()
                    .onErrorComplete()
        } else {
            Completable.complete()
        }.andThen(repository.getCurrencyRates(fromCurrency, toCurrency)
                          .flatMap { baseResponse ->

                              val list = baseResponse.data!!

                              database.saveExchangeRates(list.flatMap { it.exchangeRates })
                              database.updateBranches(list)

                              preferencesRepository.saveLastDateBank(bank, fromCurrency, toCurrency, Date())

                              database.getBranchCurrencyRates(bank.bankId, fromCurrency.id, toCurrency.id).map { Triple<List<BranchCurrency>, Date?, Boolean>(it.sort(sort), null, false) }
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

    private fun List<BranchCurrency>.sort(sort: RateCurrencySort): List<BranchCurrency> {
        return if (sort == RateCurrencySort.BUY) {
            this.sortedWith(compareBy<BranchCurrency> { it.branchRate.sellRate }.then(compareBy { it.branch.id }))
        } else {
            this.sortedWith(compareByDescending<BranchCurrency> { it.branchRate.buyRate }.then(compareBy { it.branch.id }))
        }
    }

    fun sortBanksRates(list: List<BranchCurrency>, sort: RateCurrencySort): List<BranchCurrency> {
        return list.sort(sort)
    }

}