package com.codezfox.exchangeratesmvp

import androidx.multidex.MultiDexApplication
import com.codezfox.exchangeratesmvp.di.appModule
import com.codezfox.exchangeratesmvp.di.serverModule
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class App : MultiDexApplication(), KodeinAware {

    override val kodein: Kodein by lazy {
        Kodein {
            import(serverModule)
            import(appModule)
            import(androidXModule(this@App))
        }
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}