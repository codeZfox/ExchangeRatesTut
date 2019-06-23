package com.codezfox.exchangeratesmvp.di

import android.arch.persistence.room.Room
import com.codezfox.exchangeratesmvp.data.repositories.SystemRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DataBaseRepositoryImpl
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepositoryImpl
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import org.kodein.di.Kodein
import org.kodein.di.android.ActivityRetainedScope
import org.kodein.di.generic.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val appModule = Kodein.Module("appModule") {

    bind() from scoped(ActivityRetainedScope).singleton { Cicerone.create() }

    bind() from scoped(ActivityRetainedScope).singleton { instance<Cicerone<Router>>().navigatorHolder }

    bind() from scoped(ActivityRetainedScope).singleton { instance<Cicerone<Router>>().router }

    bind<PreferencesRepository>() with singleton { PreferencesRepositoryImpl(instance()) }

    bind() from eagerSingleton {
        Room.databaseBuilder(instance(),
                RoomDatabase::class.java, "exchangerates.db")
                .build()
    }

    bind<DataBaseRepository>() with singleton { DataBaseRepositoryImpl(instance()) }

    bind<SystemRepository>() with singleton { SystemRepository(instance()) }


}