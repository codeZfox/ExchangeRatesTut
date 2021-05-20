package com.codezfox.exchangeratesmvp

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.ui.base.BackAware
import com.codezfox.exchangeratesmvp.ui.settings.NightModeType
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import java.util.*


class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val navigatorHolder: NavigatorHolder by instance()

    private val router: Router by instance()

    private val navigator = SupportAppNavigator(this, R.id.container)

    private val preferencesRepository: PreferencesRepository by instance()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context? {
        val locale = Locale("ru")
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else {
            updateResourcesLocaleLegacy(context, locale)
        }
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        return context.createConfigurationContext(Configuration(context.resources.configuration).apply { setLocale(locale) });
    }

    @SuppressWarnings("deprecation")
    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        context.resources.updateConfiguration(context.resources.configuration.apply { this.locale = locale }, context.resources.displayMetrics)
        return context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkNightMode()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        updateRemoteConfig()

        val frameLayout = FrameLayout(this).also {
            it.id = R.id.container
        }
        setContentView(frameLayout)

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.Main())
        }
    }


    private fun checkNightMode() {
        val savedNightModeValue = preferencesRepository.getSavedNightMode()
        val selectedNightMode = NightModeType.fromValue(savedNightModeValue)
        AppCompatDelegate.setDefaultNightMode(selectedNightMode.value)
    }

    private fun updateRemoteConfig() {
        Firebase.remoteConfig.apply {
            fetchAndActivate()
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.container)
                .let { fragment ->
                    if (fragment !is BackAware || !fragment.onBackPressed()) {
                        super.onBackPressed()
                    }
                }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
