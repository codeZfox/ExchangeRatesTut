package com.codezfox.exchangeratesmvp.data.repositories.preferences

import android.content.Context
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.Currency
import java.util.*

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {

    companion object {
        private const val PREFERENCES_TAG = "EXCHANGERATES"
        private const val PREF_LAST_DATE_DATA = "PREF_LAST_DATE_DATA"
        private const val PREF_SAVED_NIGHT_MODE = "PREF_SAVED_NIGHT_MODE"

    }

    private val preferences = context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE)

    private fun getLastDate(prefName: String): Date? {
        val time = preferences.getLong(prefName, 0)
        return if (time == 0L) {
            null
        } else {
            Date(time)
        }
    }

    private fun saveLastDate(prefName: String, date: Date) {
        preferences.edit().putLong(prefName, date.time).apply()
    }


    override fun saveLastDateData(date: Date) {
        saveLastDate(PREF_LAST_DATE_DATA, date)
    }

    override fun getLastDateData(): Date? {
        return getLastDate(PREF_LAST_DATE_DATA)

    }


    private fun getPrefNameCurrency(currency: Currency) = PREF_LAST_DATE_DATA + "_Currency_" + currency.id

    override fun saveLastDateCurrency(currency: Currency, date: Date) {
        saveLastDate(getPrefNameCurrency(currency), date)
    }

    override fun getLastDateCurrency(currency: Currency): Date? {
        return getLastDate(getPrefNameCurrency(currency))
    }


    private fun getPrefNameBank(bank: Bank, fromCurrency: Currency, toCurrency: Currency) = """${PREF_LAST_DATE_DATA}_${bank.bankId}_${fromCurrency.id}_${toCurrency.id}"""

    override fun saveLastDateBank(bank: Bank, fromCurrency: Currency, toCurrency: Currency, date: Date) {
        saveLastDate(getPrefNameBank(bank, fromCurrency, toCurrency), date)
    }

    override fun getLastDateBank(bank: Bank, fromCurrency: Currency, toCurrency: Currency): Date? {
        return getLastDate(getPrefNameBank(bank, fromCurrency, toCurrency))
    }


    private fun getPrefNameBranch(branch: Branch) = PREF_LAST_DATE_DATA + "_Branch_" + branch.id

    override fun saveLastDateBranch(branch: Branch, date: Date) {
        saveLastDate(getPrefNameBranch(branch), date)
    }

    override fun getLastDateBranch(branch: Branch): Date? {
        return getLastDate(getPrefNameBranch(branch))
    }

    override fun getSavedNightMode(default: Int): Int {
        return preferences.getInt(PREF_SAVED_NIGHT_MODE, default)
    }

    override fun saveNightMode(value: Int) {
        preferences.edit().putInt(PREF_SAVED_NIGHT_MODE, value).apply()
    }
}