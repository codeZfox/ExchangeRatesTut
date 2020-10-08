package com.codezfox.exchangeratesmvp.data.models

import androidx.room.Embedded
import androidx.room.Entity
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem

@Entity(primaryKeys = ["fromCurrency", "bankId"])
data class BankRate(

    @Embedded
    val bank: Bank,

    val buy: Double, //"2.120000",
    val sell: Double, //"2.132000",
    val updateTime: Long, //1539440743,
    val nationalCurrencyId: String, //"BYN",
    val fromCurrency: String //"USD"

) : DisplayableItem {

    override fun areItemsTheSame(): String {
        return bank.name
    }

}