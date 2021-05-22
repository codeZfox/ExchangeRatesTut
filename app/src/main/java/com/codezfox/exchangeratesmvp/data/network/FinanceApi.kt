package com.codezfox.exchangeratesmvp.data.network

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface FinanceApi {

    @FormUrlEncoded
    @POST("export/info_for_pda.php")
    fun getInfo(@FieldMap fields: Map<String, String>): Call<JsonObject>

    @FormUrlEncoded
    @POST("export/info_for_pda.php")
    fun getInfoSingle(@FieldMap fields: Map<String, String>): Single<JsonObject>

    @GET()
    fun getCurrencies(@Url url: String): Single<List<NBCurrency>>

    @GET()
    fun getNBBestRates(@Url url: String): Single<List<NBRate>>

}

data class NBCurrency(

        @SerializedName("Cur_ID")
        val curId: Int, //1

        @SerializedName("Cur_ParentID")
        val curParentId: Int, // 1

        @SerializedName("Cur_Code")
        val curCode: String, //"008"

        @SerializedName("Cur_Abbreviation")
        val curAbbreviation: String, //"ALL"

        @SerializedName("Cur_Name")
        val curName: String, //"Албанский лек"

        @SerializedName("Cur_Name_Bel")
        val curNameBel: String, //"Албанскі лек"

        @SerializedName("Cur_Name_Eng")
        val curNameEng: String, //"Albanian Lek"

        @SerializedName("Cur_QuotName")
        val curQuotName: String, //"1 Албанский лек"

        @SerializedName("Cur_QuotName_Bel")
        val curQuotNameBel: String, //"1 Албанскі лек"

        @SerializedName("Cur_QuotName_Eng")
        val curQuotNameEng: String, //"1 Albanian Lek"

        @SerializedName("Cur_NameMulti")
        val curNameMulti: String, //""

        @SerializedName("Cur_Name_BelMulti")
        val curNameBelMulti: String, //""

        @SerializedName("Cur_Name_EngMulti")
        val curNameEngMulti: String, //""

        @SerializedName("Cur_Scale")
        val curScale: Int, //1

        @SerializedName("Cur_Periodicity")
        val curPeriodicity: Int, //1

        @SerializedName("Cur_DateStart")
        val curDateStart: Date, //"1991-01-01T00:00:00"

        @SerializedName("Cur_DateEnd")
        val curDateEnd: Date //"2007-11-30T00:00:00"
)

data class NBRate(
        @SerializedName("Cur_ID")
        val curId: Int, //170

        @SerializedName("Date")
        val date: Date, //"2021-05-20T00:00:00"

        @SerializedName("Cur_Abbreviation")
        val curAbbreviation: String, //"AUD"

        @SerializedName("Cur_Scale")
        val curScale: Int, //1

        @SerializedName("Cur_Name")
        val curName: String, //"Австралийский доллар"

        @SerializedName("Cur_OfficialRate")
        val curOfficialRate: Double // 1.9485
)