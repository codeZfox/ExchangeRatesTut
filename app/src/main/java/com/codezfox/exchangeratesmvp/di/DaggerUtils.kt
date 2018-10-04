package com.codezfox.exchangeratesmvp.di

import android.content.Context
import com.codezfox.exchangeratesmvp.BuildConfig

object DaggerUtils {

    lateinit var appComponent: AppComponent

    fun buildComponents(context: Context) {
        appComponent = buildAppComponent(context)
    }

    private fun buildAppComponent(context: Context) = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .serverModule(ServerModule(BuildConfig.ORIGIN_ENDPOINT))
            .build()


}