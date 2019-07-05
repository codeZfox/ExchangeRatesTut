package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import io.reactivex.Single

class DatabaseRepositoryImpl(roomDatabase: RoomDatabase) : DatabaseRepository {

    private val currencyDao = roomDatabase.currencyDao()
    private val rateDao = roomDatabase.rateDao()
    private val rateBankDao = roomDatabase.rateBankDao()
    private val branchDao = roomDatabase.branchDao()

    override fun saveCurrencies(currencies: List<Currency>) {
        currencyDao.deleteCurrencies()
        currencyDao.insertCurrency(currencies)
    }

    override fun saveRates(rates: List<Rate>) {
        rateDao.deleteRates()
        rateDao.insertRates(rates)
    }

    override fun getBestRates(): Single<List<RateCurrency>> {
        return rateDao.getRateCurrencies()
    }

    override fun saveBanksRates(rates: List<RateBank>) {
//        rateBankDao.deleteRates()
        rateBankDao.insertRates(rates)
    }

    override fun getBanksRates(currency: Currency): Single<List<RateBank>> {
        return rateBankDao.getRates(currency.id)
    }

    override fun saveBranches(branches: List<Branch>) {
        branchDao.insertBranches(branches)
    }

    override fun updateBranches(branches: List<BranchRate>) {
        val list = branchDao.getBranches()
        branches.forEach { branchRate ->
            list.find { it.id == branchRate.branche_id }?.isOpened = branchRate.isOpened
        }
        branchDao.updateBranches(list)
    }

    override fun saveExchangeRates(branches: List<ExchangeRate>) {
        branchDao.insertExchangeRates(branches)
    }

    override fun getBranchCurrencyRates(bankId: String, fromCurrency: String, toCurrency: String): Single<List<BranchCurrency>> {
        return branchDao.getBranchesCurrencies(bankId, fromCurrency, toCurrency)
    }
}