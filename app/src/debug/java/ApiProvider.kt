package com.codezfox.exchangeratesmvp.data.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class ApiProviderImpl(
        baseUrl: String,
        gson: Gson
) : ApiProvider(baseUrl, gson) {

    override fun interceptors(): List<Interceptor> {
        return listOf(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        })
    }

}