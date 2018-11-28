package com.codezfox.exchangeratesmvp.presentation.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.presentation.Screens
import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.presentation.view.banksrates.BanksRatesFragment
import com.codezfox.exchangeratesmvp.presentation.view.currencyrates.CurrencyRatesFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.container) {

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String?) {
//            toast(message ?: "null")
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment? {
            return when (screenKey) {
                Screens.CURRENCY_RATES -> CurrencyRatesFragment()
                Screens.CURRENCY_BANKS -> BanksRatesFragment().also { fragment ->
                    fragment.arguments = Bundle().also {
                        if (data is Currency) {
                            it.putSerializable("Currency", data)
                        }
                    }
                }
                else -> null
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frameLayout = FrameLayout(this).also {
            it.id = R.id.container
        }
        setContentView(frameLayout)

        DaggerUtils.appComponent.inject(this)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.CURRENCY_RATES, null)))
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
