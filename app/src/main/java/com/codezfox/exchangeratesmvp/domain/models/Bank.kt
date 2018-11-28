package com.codezfox.exchangeratesmvp.domain.models

import com.google.gson.annotations.SerializedName

data class Bank(

        @SerializedName("bank_id")
        val bankId: String, //"43"

        val name: String, //"Технобанк"

        val isOpened: String //"1"

)