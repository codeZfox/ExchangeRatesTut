package com.codezfox.exchangeratesmvp.domain.models

class Place(
        val id: String, // "6/4616"
        val branche_id: String, // "5719"
        val isOpened: String, // "1"
        val exchangeRates: List<ExchangeRate>
)

class ExchangeRate(
        val nationalCurrencyId: String, // "BYN"
        val foreignCurrencyId: String, // "USD"
        val sellRate: String, // "2.138000"
        val buyRate: String, // "2.125000"
        val source: String, // "bank"
        val updateTime: Long // 1543836312
)