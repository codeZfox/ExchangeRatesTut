package com.codezfox.exchangeratesmvp.data.server

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider(
        private val baseUrl: String,
        private val gson: Gson
) {

    fun get() =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(buildOkHttpClient())
                    .baseUrl(baseUrl)
                    .build()
                    .create(FinanceApi::class.java)

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().also {
            it.addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

}