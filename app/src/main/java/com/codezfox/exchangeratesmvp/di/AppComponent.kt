package com.codezfox.exchangeratesmvp.di

import com.codezfox.exchangeratesmvp.domain.banksrates.BanksRatesInteractor
import com.codezfox.exchangeratesmvp.domain.currencyrates.CurrencyRatesInteractor
import com.codezfox.exchangeratesmvp.presentation.presenter.banksrates.BanksRatesPresenter
import com.codezfox.exchangeratesmvp.presentation.presenter.currencyrates.CurrencyRatesPresenter
import com.codezfox.exchangeratesmvp.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ServerModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(presenter: CurrencyRatesPresenter)

    fun inject(presenter: BanksRatesPresenter)

    fun inject(interactor: CurrencyRatesInteractor)

    fun inject(interactor: BanksRatesInteractor)

}