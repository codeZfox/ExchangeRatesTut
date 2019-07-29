package com.codezfox.exchangeratesmvp.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

class RatesOfBranch(
        val id: String, // "6/4616"
        val branche_id: String, // "5719"
        val isOpened: String, // "1"
        val exchangeRates: List<ExchangeRate>
)

@Entity(primaryKeys = ["branche_id", "nationalCurrencyId", "foreignCurrencyId"])
open class ExchangeRate(
        var branche_id: String,
        val nationalCurrencyId: String, // "BYN"
        val foreignCurrencyId: String, // "USD"
        val sellRate: Double, // "2.138000"
        val buyRate: Double, // "2.125000"
        val source: String, // "bank"
        val updateTime: Long // 1543836312
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeRate

        if (branche_id != other.branche_id) return false
        if (nationalCurrencyId != other.nationalCurrencyId) return false
        if (foreignCurrencyId != other.foreignCurrencyId) return false
        if (sellRate != other.sellRate) return false
        if (buyRate != other.buyRate) return false
        if (source != other.source) return false
        if (updateTime != other.updateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = branche_id.hashCode()
        result = 31 * result + nationalCurrencyId.hashCode()
        result = 31 * result + foreignCurrencyId.hashCode()
        result = 31 * result + sellRate.hashCode()
        result = 31 * result + buyRate.hashCode()
        result = 31 * result + source.hashCode()
        result = 31 * result + updateTime.hashCode()
        return result
    }
}

@Entity(primaryKeys = ["branche_id", "nationalCurrencyId", "foreignCurrencyId"])
data class ExchangeRateBranch(
        var branche_id: String,
        val nationalCurrencyId: String, // "BYN"
        val foreignCurrencyId: String, // "USD"
        val sellRate: Double, // "2.138000"
        val buyRate: Double, // "2.125000"
        val source: String, // "bank"
        val updateTime: Long // 1543836312
) {
    constructor(exchangeRate: ExchangeRate) : this(exchangeRate.branche_id,
            exchangeRate.nationalCurrencyId,
            exchangeRate.foreignCurrencyId,
            exchangeRate.sellRate,
            exchangeRate.buyRate,
            exchangeRate.source,
            exchangeRate.updateTime)
}

data class BranchWithExchangeRate(

        @Embedded
        var branch: Branch,

        @Embedded
        var branchRate: ExchangeRate

)

data class CurrencyExchangeRate(

        @Embedded
        var currency: Currency,

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
) : Serializable {

    fun getPhonesList() = phone.split(", ").filterNot { it.isEmpty() }

}

data class Schedule(
        val days: Int, // 120
        val open: String, // "30600"
        val close: String // "63000"
) : Serializable