package com.codezfox.exchangeratesmvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val navigatorHolder: NavigatorHolder by instance()

    private val router: Router by instance()

    private val navigator = SupportAppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frameLayout = FrameLayout(this).also {
            it.id = R.id.container
        }
        setContentView(frameLayout)

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.CurrencyRates())
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
