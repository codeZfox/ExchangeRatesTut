package com.codezfox.exchangeratesmvp.model.repository.database

import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateCurrency
import com.codezfox.exchangeratesmvp.model.data.database.RoomDatabase

class DataBaseRepositoryImpl(roomDatabase: RoomDatabase) : DataBaseRepository {

    private val currencyDao = roomDatabase.currencyDao()
    private val rateDao = roomDatabase.rateDao()

    override fun saveCurrencies(currencies: List<Currency>) {
        currencyDao.deleteCurrencies()
        currencyDao.insertCurrency(currencies)
    }

    override fun saveRates(rates: List<Rate>) {
        rateDao.deleteRates()
        rateDao.insertRates(rates)
    }

    override fun get(): List<RateCurrency> {
        return rateDao.getRatess()
    }


}