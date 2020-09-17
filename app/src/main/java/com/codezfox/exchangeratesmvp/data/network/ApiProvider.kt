package com.codezfox.exchangeratesmvp.data.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiProvider(
        private val baseUrl: String,
        private val gson: Gson
) {

    fun get() =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(buildOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                    .create(FinanceApi::class.java)

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().also { builder ->
            interceptors().forEach {
                builder.addInterceptor(it)
            }
        }.build()
    }

    abstract fun interceptors(): List<Interceptor>

}