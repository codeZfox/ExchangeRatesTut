package com.codezfox.exchangeratesmvp.data.repositories.preferences

import com.codezfox.exchangeratesmvp.data.models.Currency
import java.util.*

interface PreferencesRepository {

    fun saveLastDateData(date: Date)

    fun getLastDateData(): Date?

    fun saveLastDateCurrency(currency: Currency, date: Date)

    fun getLastDateCurrency(currency: Currency): Date?

}

