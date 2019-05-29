package com.codezfox.exchangeratesmvp.di

import android.arch.persistence.room.Room
import com.codezfox.exchangeratesmvp.data.repositories.database.DataBaseRepositoryImpl
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepositoryImpl
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val appModule = Kodein.Module("appModule") {

    val cicerone: Cicerone<Router> = Cicerone.create()

    bind<PreferencesRepository>() with singleton { PreferencesRepositoryImpl(instance()) }

    bind() from eagerSingleton { cicerone.navigatorHolder }

    bind() from eagerSingleton { cicerone.router }

    bind() from eagerSingleton {
        Room.databaseBuilder(instance(),
                RoomDatabase::class.java, "exchangerates.db")
                .build()
    }

    bind<DataBaseRepository>() with singleton { DataBaseRepositoryImpl(instance()) }


}