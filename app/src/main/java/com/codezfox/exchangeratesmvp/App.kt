package com.codezfox.exchangeratesmvp

import androidx.multidex.MultiDexApplication
import com.codezfox.exchangeratesmvp.data.config.Group
import com.codezfox.exchangeratesmvp.di.appModule
import com.codezfox.exchangeratesmvp.di.serverModule
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import java.util.concurrent.TimeUnit


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

        Firebase.remoteConfig.apply {
            setDefaultsAsync(Group.BestRates().mapDefaultValues())
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = TimeUnit.HOURS.toSeconds(1)
            })
        }
    }
}