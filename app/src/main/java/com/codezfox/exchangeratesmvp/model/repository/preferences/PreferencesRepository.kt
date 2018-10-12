package com.codezfox.exchangeratesmvp.model.repository.preferences

import java.util.*

interface PreferencesRepository {

    fun saveLastDateData(date: Date)

    fun getLastDateData(): Date?

}

