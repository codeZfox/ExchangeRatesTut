package com.codezfox.exchangeratesmvp.di

import com.codezfox.exchangeratesmvp.model.interactor.currencyrates.CurrencyRatesInteractor
import com.codezfox.exchangeratesmvp.presentation.currencyrates.CurrencyRatesPresenter
import com.codezfox.exchangeratesmvp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ServerModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(presenter: CurrencyRatesPresenter)

    fun inject(interactor: CurrencyRatesInteractor)

}