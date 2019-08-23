package com.codezfox.exchangeratesmvp.ui.converter

import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import io.reactivex.Single
import java.util.*


class ConverterInteractor(
        private val database: DatabaseRepository,
        private val preferencesRepository: PreferencesRepository
) {

    fun loadRates(): Single<Triple<List<BestRateCurrency>, Date?, Boolean>> {

        val date = preferencesRepository.getLastDateData()

        return database.getBestRatesCurrencies().map { list ->
            Triple(list, date, true)
        }

    }

}