package com.codezfox.exchangeratesmvp

import android.app.Application
import com.codezfox.exchangeratesmvp.di.appModule
import com.codezfox.exchangeratesmvp.di.serverModule
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.androidModule
import org.kodein.di.android.support.androidSupportModule

class App : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())

    }

    override val kodein: Kodein by lazy {
        Kodein {
            import(serverModule)
            import(appModule)
            import(androidSupportModule(this@App))
        }
    }
}