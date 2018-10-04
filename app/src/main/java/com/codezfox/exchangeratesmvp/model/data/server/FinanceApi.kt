package com.codezfox.exchangeratesmvp.model.data.server

import com.codezfox.exchangeratesmvp.entity.CurrencyRate
import com.codezfox.exchangeratesmvp.entity.CurrencyRateResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FinanceApi {

    @FormUrlEncoded
    @POST("export/info_for_pda.php")
    fun getRates(
            @Field("action") action: String,
            @Field("auth_key") auth_key: String,
            @Field("params") params: String
    ): Call<CurrencyRateResponse>

}