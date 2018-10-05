package com.codezfox.exchangeratesmvp.extensions

import retrofit2.Call
import retrofit2.HttpException


fun <T> Call<T>.bodyOrError(): T {
    return this.execute().bodyOrError()
}

fun <T> retrofit2.Response<T>.bodyOrError(): T {
    if (this.isSuccessful) {
        return this.body()!!
    }

    throw HttpException(this)
}

fun <T> Call<T>.isSuccessfulOrError(): Boolean {
    return this.execute().isSuccessfulOrError()
}

fun <T> retrofit2.Response<T>.isSuccessfulOrError(): Boolean {
    if (this.isSuccessful) {
        return true
    }

    throw HttpException(this)
}
