package com.codezfox.exchangeratesmvp.data.network


import com.google.gson.Gson
import okhttp3.Interceptor

class ApiProviderImpl(
        baseUrl: String,
        gson: Gson
) : ApiProvider(baseUrl, gson) {

    override fun interceptors(): List<Interceptor> {
        return emptyList()
    }

}