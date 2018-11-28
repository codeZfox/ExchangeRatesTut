package com.codezfox.exchangeratesmvp.domain

import java.util.*

interface PreferencesRepository {

    fun saveLastDateData(date: Date)

    fun getLastDateData(): Date?

}

