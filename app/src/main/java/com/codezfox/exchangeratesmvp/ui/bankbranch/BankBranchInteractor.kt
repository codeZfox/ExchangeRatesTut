package com.codezfox.exchangeratesmvp.ui.bankbranch

import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class BankBranchInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {
    fun getCurrencyRates(branch: Branch): Single<Pair<Bank, List<CurrencyExchangeRate>>> {
//        return Single.zip(database.getBankById(branch.bank_id), database.getBranchCurrencyRates(branch.id), BiFunction { bank, exchangeRates ->
//            bank to exchangeRates
//        })
        return repository.getRatesOfBranch(branch.id)
                .map { list ->

                    database.saveExchangeRates(list.flatMap { it.exchangeRates })
                    database.updateBranches(list)

//                    preferencesRepository.saveLastDateBank(bank, fromCurrency, toCurrency, Date())

                    Unit
                }
                .onErrorReturnItem(Unit)
                .flatMap {
                    Single.zip<Bank, List<CurrencyExchangeRate>, Pair<Bank, List<CurrencyExchangeRate>>>(database.getBankById(branch.bank_id), database.getBranchCurrencyRates(branch.id), BiFunction { bank, exchangeRates ->
                        bank to exchangeRates
                    })
                }
    }
}