package com.codezfox.exchangeratesmvp.ui.bankbranch

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

class BankBranchInteractor(
    private val repository: CurrencyRatesRepository,
    private val database: DatabaseRepository,
    private val preferencesRepository: PreferencesRepository
) {

    fun loadCurrencyRates(branch: Branch): Completable {
        return repository.getRatesOfBranch(branch.id)
            .map { list ->
                list.flatMap { ratesOfBranch ->
                    ratesOfBranch.exchangeRates.map {
                        ExchangeRateBranch(it)
                    }
                }
            }
            .doOnSuccess { list ->

                database.saveExchangeRateBranch(list)
//                    database.updateBranches(list) //todo why

                preferencesRepository.saveLastDateBranch(branch, Date())

            }
            .ignoreElement()
            .subscribeOn(Schedulers.io())
    }

    fun getCurrencyRates(branch: Branch): Flowable<Triple<Bank, List<CurrencyExchangeRate>, Date?>> {
        val getBank = database.getBankById(branch.bank_id)
        val getBranchCurrencyRates = database.getBranchCurrencyRates(branch.id)
        val biFunction = BiFunction<Bank, List<CurrencyExchangeRate>, Triple<Bank, List<CurrencyExchangeRate>, Date?>> { bank, exchangeRates ->
            val date = preferencesRepository.getLastDateBranch(branch)
            Triple(bank, exchangeRates, date)
        }
        return Flowable.combineLatest(getBank, getBranchCurrencyRates, biFunction)
    }

    fun getServices(): Single<List<Service>> {
        return repository.getServices()
    }
}