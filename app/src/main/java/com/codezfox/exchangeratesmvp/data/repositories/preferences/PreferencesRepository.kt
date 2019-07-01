package com.codezfox.exchangeratesmvp.data.repositories.preferences

import java.util.*

interface PreferencesRepository {

    fun saveLastDateData(date: Date)

    fun getLastDateData(): Date?

}

