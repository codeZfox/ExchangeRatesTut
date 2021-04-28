package com.codezfox.exchangeratesmvp.extensions

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

fun Throwable.isNetworkException(): Boolean {
    return this is SocketTimeoutException
            || this is ConnectException
            || this is UnknownHostException
            || this is SSLHandshakeException
            || if (this != cause) cause?.isNetworkException() == true else false
}