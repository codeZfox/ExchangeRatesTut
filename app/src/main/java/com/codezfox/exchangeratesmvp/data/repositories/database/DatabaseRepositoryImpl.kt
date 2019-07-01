package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.Rate
import com.codezfox.exchangeratesmvp.data.models.RateBank
import com.codezfox.exchangeratesmvp.data.models.RateCurrency
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import io.reactivex.Single

class DatabaseRepositoryImpl(roomDatabase: RoomDatabase) : DatabaseRepository {

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

    override fun getBestRates(): Single<List<RateCurrency>> {
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