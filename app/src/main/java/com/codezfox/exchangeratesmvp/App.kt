package com.codezfox.exchangeratesmvp

import android.app.Application
import com.codezfox.exchangeratesmvp.di.DaggerUtils

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerUtils.buildComponents(this)
    }
}