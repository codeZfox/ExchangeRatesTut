package com.codezfox.exchangeratesmvp.ui.converter

import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*


class ConverterInteractor(
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    fun loadRates(): Flowable<List<BestRateCurrency>> {
        return database.getBestRatesCurrencies()

    }

}