package com.codezfox.exchangeratesmvp.di

import android.content.Context
import com.codezfox.exchangeratesmvp.model.data.server.ApiProvider
import com.codezfox.exchangeratesmvp.model.data.server.FinanceApi
import com.codezfox.exchangeratesmvp.model.repository.preferences.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRepository() = PreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideNavigatorHolder() = cicerone.navigatorHolder

    @Provides
    @Singleton
    fun provideRouter() = cicerone.router

}