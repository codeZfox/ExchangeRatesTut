package com.codezfox.exchangeratesmvp

import android.app.Application
import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
        DaggerUtils.buildComponents(this)
    }
}