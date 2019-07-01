package com.codezfox.exchangeratesmvp.data.repositories.preferences

import android.content.Context
import java.util.*
import com.codezfox.exchangeratesmvp.data.models.Currency

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

    override fun saveLastDateCurrency(currency: Currency, date: Date) {
        preferences.edit().putLong(getPrefNameCurrency(currency), date.time).apply()
    }

    override fun getLastDateCurrency(currency: Currency): Date? {
        val time = preferences.getLong(getPrefNameCurrency(currency), 0)
        return if (time == 0L) {
            null
        } else {
            Date(time)
        }
    }

    private fun getPrefNameCurrency(currency: Currency) = PREF_LAST_DATE_DATA + "_" + currency

}