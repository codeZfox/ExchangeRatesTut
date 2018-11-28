package com.codezfox.exchangeratesmvp.domain.banksrates

import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class BanksRatesInteractor(

        private val currency: Currency

) {

    @Inject
    lateinit var repository: CurrencyRatesRepository

    @Inject
    lateinit var database: DataBaseRepository

    init {
        DaggerUtils.appComponent.inject(this)
    }

    fun loadBanksRates(): List<RateBank> {

        try {

            val list = repository.getBanksRates(currency).data!!
            database.saveBanksRates(list)

            return list

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is SocketTimeoutException || e is UnknownHostException || e is ConnectException) {
                return database.getBanksRates(currency)
            }
            throw e
        }

    }

}