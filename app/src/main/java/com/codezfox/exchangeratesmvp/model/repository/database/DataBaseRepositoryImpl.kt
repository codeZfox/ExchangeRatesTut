package com.codezfox.exchangeratesmvp.model.repository.database

import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateBank
import com.codezfox.exchangeratesmvp.entity.RateCurrency
import com.codezfox.exchangeratesmvp.model.data.database.RoomDatabase

class DataBaseRepositoryImpl(roomDatabase: RoomDatabase) : DataBaseRepository {

    private val currencyDao = roomDatabase.currencyDao()
    private val rateDao = roomDatabase.rateDao()
    private val rateBankDao = roomDatabase.rateBankDao()

    override fun saveCurrencies(currencies: List<Currency>) {
        currencyDao.deleteCurrencies()
        currencyDao.insertCurrency(currencies)
    }

    override fun saveRates(rates: List<Rate>) {
        rateDao.deleteRates()
        rateDao.insertRates(rates)
    }

    override fun getBestRates(): List<RateCurrency> {
        return rateDao.getRatess()
    }

    override fun saveBanksRates(rates: List<RateBank>) {
//        rateBankDao.deleteRates()
        rateBankDao.insertRates(rates)
    }

    override fun getBanksRates(currency: Currency): List<RateBank> {
        return rateBankDao.getRates(currency.id)
    }
}