package com.codezfox.exchangeratesmvp.ui.bankbranch

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class BankBranchInteractor(
        private val repository: CurrencyRatesRepository,
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    fun getCurrencyRates(branch: Branch): Single<Triple<Bank, List<CurrencyExchangeRate>, Date?>> {
        val zip: (Date?) -> Single<Triple<Bank, List<CurrencyExchangeRate>, Date?>> = { date ->
            val getBank = database.getBankById(branch.bank_id)
            val getBranchCurrencyRates = database.getBranchCurrencyRates(branch.id)
            val biFunction = BiFunction<Bank, List<CurrencyExchangeRate>, Triple<Bank, List<CurrencyExchangeRate>, Date?>> { bank, exchangeRates ->
                Triple(bank, exchangeRates, date)
            }
            Single.zip(getBank, getBranchCurrencyRates, biFunction)
        }
        return repository.getRatesOfBranch(branch.id)
                .map { list ->
                    list.flatMap { ratesOfBranch ->
                        ratesOfBranch.exchangeRates.map {
                            ExchangeRateBranch(it)
                        }
                    }
                }
                .flatMap { list ->

                    database.saveExchangeRateBranch(list)
//                    database.updateBranches(list) //todo why

                    preferencesRepository.saveLastDateBranch(branch, Date())

                    zip(null)
                }
                .onErrorResumeNext {

                    val date = preferencesRepository.getLastDateBranch(branch)

                    zip(date)

                }
    }

    fun getServices(): Single<List<Service>> {
        return repository.getServices()
    }
}