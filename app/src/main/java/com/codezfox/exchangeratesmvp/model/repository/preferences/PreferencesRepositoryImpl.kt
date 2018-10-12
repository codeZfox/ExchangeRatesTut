package com.codezfox.exchangeratesmvp.model.repository.preferences

import android.content.Context
import java.util.*

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {

    companion object {
        private const val PREFERENCES_TAG = "EXCHANGERATES"
        private const val PREF_LAST_DATE_DATA = "PREF_LAST_DATE_DATA"

    }

    private val preferences = context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE)

    override fun saveLastDateData(date: Date) {
        preferences.edit().putLong(PREF_LAST_DATE_DATA, date.time).apply()
    }

    override fun getLastDateData(): Date? {
        val time = preferences.getLong(PREF_LAST_DATE_DATA, 0)
        return if (time == 0L) {
            null
        } else {
            Date(time)
        }
    }
}