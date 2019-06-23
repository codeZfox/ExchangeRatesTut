package com.codezfox.exchangeratesmvp.data.repositories

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


class SystemRepository(private val context: Context) {

    private val subject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getNetworkConnected(): Observable<Boolean> = subject.hide().observeOn(Schedulers.io())

    fun getNetworkConnected(isNetworkConnected: Boolean): Observable<Boolean> = getNetworkConnected().filter { it == isNetworkConnected }

    fun isNetworkConnected() = context.isNetworkConnected()

    init {
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(NetworkChangeReceiver(), intentFilter)
    }

    inner class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            subject.onNext(context.isNetworkConnected())
        }
    }

    fun Context.isNetworkConnected(): Boolean {
        return (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}