package com.codezfox.exchangeratesmvp.domain.models

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("exchangeRates", alternate = ["currencies"])
    var data: List<T>? = null
    var ts: Long = 0L
    var status: String? = null
}