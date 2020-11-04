package com.codezfox.exchangeratesmvp

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object Analytics {
    fun logScreen(screen: Screen) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screen.name)
        }
    }
}

sealed class Screen(val name: String) {
    object BestRates : Screen("best_rates")
    //refresh
    object Converter : Screen("converter")

    // current currency
    object RateCurrencyOfBanks : Screen("rate_currency_of_banks") // currency.id
    object ExchangeRateCurrencyOfBranchesBank : Screen("exchange_rate_currency_of_branches_bank") //bank and currency
    object BankBranch : Screen("bank_branch") //bank, branch, currency
    // btn - currency
    // btn - услуги
    // btn - услуги- услуга
    // btn - адрес
    // btn - название

    // btn - back
}