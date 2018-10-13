package com.codezfox.exchangeratesmvp.model.data.server

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider

class ApiProvider(
        private val baseUrl: String,
        private val gson: Gson
) : Provider<FinanceApi> {

    override fun get() =
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