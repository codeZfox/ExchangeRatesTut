package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.*
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import io.reactivex.Single

class DatabaseRepositoryImpl(roomDatabase: RoomDatabase) : DatabaseRepository {

    private val currencyDao = roomDatabase.currencyDao()
    private val rateDao = roomDatabase.rateDao()
    private val rateBankDao = roomDatabase.rateBankDao()
    private val branchDao = roomDatabase.branchDao()
    private val serviceDao = roomDatabase.serviceDao()

    override fun saveCurrencies(currencies: List<Currency>) {
        currencyDao.deleteCurrencies()
        currencyDao.insertCurrency(currencies.mapIndexed { index, currency ->  currency.copy(order = index) })
    }

    override fun saveBestRates(rates: List<BestRate>) {
//        rateDao.deleteBestRates()
        rateDao.insertBestRates(rates)
    }

    override fun getBestRatesCurrencies(): Single<List<BestRateCurrency>> {
        return rateDao.getBestRatesCurrencies().map { it.sortedBy { it.currency.order } }
    }

    override fun saveBanksRates(rates: List<BankRate>) {
//        rateBankDao.deleteBestRates()
        rateBankDao.insertBankRates(rates)
    }

    override fun getBanksRates(currency: Currency): Single<List<BankRate>> {
        return rateBankDao.getBankRates(currency.id)
    }

    override fun saveBranches(branches: List<Branch>) {
        branchDao.insertBranches(branches)
    }

    override fun updateBranches(branches: List<RatesOfBranch>) {
        val list = branchDao.getBranches()
        branches.forEach { branchRate ->
            list.find { it.id == branchRate.branche_id }?.isOpened = branchRate.isOpened
        }
        branchDao.updateBranches(list)
    }

    override fun saveExchangeRates(branches: List<ExchangeRate>) {
        branchDao.insertExchangeRates(branches)
    }

    override fun getBranchCurrencyRates(branchId: String): Single<List<CurrencyExchangeRate>> {
        return branchDao.getCurrencyExchangeRate(branchId)
    }

    override fun saveExchangeRateBranch(branches: List<ExchangeRateBranch>) {
        branchDao.insertExchangeRateBranch(branches)
    }

    override fun getBranchCurrencyRates(bankId: String, fromCurrency: String, toCurrency: String): Single<List<BranchWithExchangeRate>> {
        return branchDao.getBranchesCurrencies(bankId, fromCurrency, toCurrency)
    }

    override fun getBankById(bankId: String): Single<Bank> {
        return rateBankDao.getBankById(bankId).map { it.bank }
    }

    override fun insertService(list: List<Service>) {
        serviceDao.insertService(list)
        serviceDao.deleteServices()
    }

    override fun deleteServices() {
        serviceDao.deleteServices()
    }
}