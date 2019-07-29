package com.codezfox.exchangeratesmvp.data.models

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("exchangeRates", alternate = ["currencies", "places", "services"])
    var data: List<T>? = null
    var ts: Long = 0L
    var status: String? = null
}