package com.codezfox.exchangeratesmvp.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

class BranchRate(
        val id: String, // "6/4616"
        val branche_id: String, // "5719"
        val isOpened: String, // "1"
        val exchangeRates: List<ExchangeRate>
)

@Entity(primaryKeys = ["branche_id", "nationalCurrencyId", "foreignCurrencyId"])
data class ExchangeRate(
        var branche_id: String,
        val nationalCurrencyId: String, // "BYN"
        val foreignCurrencyId: String, // "USD"
        val sellRate: Double, // "2.138000"
        val buyRate: Double, // "2.125000"
        val source: String, // "bank"
        val updateTime: Long // 1543836312
)

data class BranchCurrency(

        @Embedded
        var branch: Branch,

        @Embedded
        var branchRate: ExchangeRate

)

@Entity
data class Branch(
        val bank_id: String, // "27"
        val filial_id: String, // "4705"
        val name: String, // "Паритетбанк"
        val city_id: String, // "15800"
        val address: String, // "г. Минск, улица Киселева, 61а"
        val phone: String, // "+375 29 5454949, +375 44 5454949, +375 33 5454949, +375 25 5454949"
        val region: String, // "minsk"
        val placeType: String, // "branch"
        @PrimaryKey val id: String, // "4705"
        val is_main: String, // "1"
        val services: List<String>?,
        val schedule: List<Schedule>?,
        val lon: String, // "27.5604"
        val lat: String, // "53.9150"
        val branche_type: String, // "7"
        var isOpened: String? = null
)

data class Schedule(
        val days: Int, // 120
        val open: String, // "30600"
        val close: String // "63000"
)