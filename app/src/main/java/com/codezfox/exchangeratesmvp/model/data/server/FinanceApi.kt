package com.codezfox.exchangeratesmvp.model.data.server

import com.codezfox.exchangeratesmvp.entity.BaseResponse
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.Rate
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FinanceApi {

    companion object {
        private const val URL = "export/info_for_pda.php"
    }

    @FormUrlEncoded
    @POST(URL)
    fun getCurrencyRate(@FieldMap fields: Map<String, String>): Call<BaseResponse<Rate>>

    @FormUrlEncoded
    @POST(URL)
    fun getCurrencies(@FieldMap fields: Map<String, String>): Call<BaseResponse<Currency>>

}