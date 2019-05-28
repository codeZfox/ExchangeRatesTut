package com.codezfox.exchangeratesmvp.presentation.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.presentation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator = SupportAppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frameLayout = FrameLayout(this).also {
            it.id = R.id.container
        }
        setContentView(frameLayout)

        DaggerUtils.appComponent.inject(this)

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
