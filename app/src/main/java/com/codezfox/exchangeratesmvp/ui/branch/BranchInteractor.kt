package com.codezfox.exchangeratesmvp.ui.branch

import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.BranchCurrency2
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class BranchInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {
    fun getCurrencyRates(branch: Branch): Single<Pair<Bank, List<BranchCurrency2>>> {
//        return Single.zip(database.getBankById(branch.bank_id), database.getBranchCurrencyRates(branch.id), BiFunction { bank, exchangeRates ->
//            bank to exchangeRates
//        })
        return repository.getBrancheExchangeRate(branch.id)
                .map { baseResponse ->
                    val list = baseResponse.data!!

                    database.saveExchangeRates(list.flatMap { it.exchangeRates })
                    database.updateBranches(list)

//                    preferencesRepository.saveLastDateBank(bank, fromCurrency, toCurrency, Date())

                    Unit
                }
                .onErrorReturnItem(Unit)
                .flatMap {
                    Single.zip<Bank, List<BranchCurrency2>, Pair<Bank, List<BranchCurrency2>>>(database.getBankById(branch.bank_id), database.getBranchCurrencyRates(branch.id), BiFunction { bank, exchangeRates ->
                        bank to exchangeRates
                    })
                }
    }
}