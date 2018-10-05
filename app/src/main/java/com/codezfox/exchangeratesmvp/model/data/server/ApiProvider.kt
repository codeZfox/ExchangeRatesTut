package com.codezfox.exchangeratesmvp.model.data.server

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Provider

class ApiProvider(
        private val baseUrl: String
) : Provider<FinanceApi> {

    override fun get() =
            Retrofit.Builder()
                    .addConverterFactory(buildGSON())
                    .client(buildOkHttpClient())
                    .baseUrl(baseUrl)
                    .build()
                    .create(FinanceApi::class.java)

    private fun buildGSON(): GsonConverterFactory? {
        val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {

                    val df = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

                    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
                        return try {
                            df.parse(json?.getAsString())
                        } catch (e: ParseException) {
                            null
                        }

                    }
                }).create()

        return GsonConverterFactory.create(gson)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().also {
            it.addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

}