package com.codezfox.exchangeratesmvp.di

import com.codezfox.exchangeratesmvp.model.data.server.ApiProvider
import com.codezfox.exchangeratesmvp.model.data.server.FinanceApi
import com.codezfox.exchangeratesmvp.model.repository.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.model.repository.currencyrates.CurrencyRatesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ServerModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideApi(): FinanceApi = ApiProvider(baseUrl).get()

    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(api: FinanceApi): CurrencyRatesRepository = CurrencyRatesRepositoryImpl(api)


}