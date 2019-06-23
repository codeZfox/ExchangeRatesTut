package com.codezfox.exchangeratesmvp.data.repositories.database

import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.Rate
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import io.reactivex.Single

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