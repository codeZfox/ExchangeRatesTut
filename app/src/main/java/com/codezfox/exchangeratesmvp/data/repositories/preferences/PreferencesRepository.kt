package com.codezfox.exchangeratesmvp.data.repositories.preferences

import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.Currency
import java.util.*

interface PreferencesRepository {

    fun saveLastDateData(date: Date)

    fun getLastDateData(): Date?

    fun saveLastDateCurrency(currency: Currency, date: Date)

    fun getLastDateCurrency(currency: Currency): Date?

    fun saveLastDateBank(bank: Bank, fromCurrency: Currency, toCurrency: Currency, date: Date)

    fun getLastDateBank(bank: Bank, fromCurrency: Currency, toCurrency: Currency): Date?

    fun saveLastDateBranch(branch: Branch, date: Date)

    fun getLastDateBranch(branch: Branch): Date?

}

