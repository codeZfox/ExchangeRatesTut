package com.codezfox.exchangeratesmvp.data.server

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FinanceApi {

    @FormUrlEncoded
    @POST("export/info_for_pda.php")
    fun getInfo(@FieldMap fields: Map<String, String>): Call<JsonObject>

}