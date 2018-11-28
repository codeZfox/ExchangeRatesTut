package com.codezfox.exchangeratesmvp.di

import android.arch.persistence.room.Room
import android.content.Context
import com.codezfox.exchangeratesmvp.data.repositories.database.DataBaseRepositoryImpl
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import com.codezfox.exchangeratesmvp.domain.DataBaseRepository
import com.codezfox.exchangeratesmvp.domain.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun providePreferencesRepository(): PreferencesRepository = PreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideNavigatorHolder() = cicerone.navigatorHolder

    @Provides
    @Singleton
    fun provideRouter() = cicerone.router

    @Singleton
    @Provides
    fun provideDatabase(): RoomDatabase {
        return Room.databaseBuilder(context,
                RoomDatabase::class.java, "exchangerates.db")
                .build()
    }

    @Singleton
    @Provides
    fun provideDataBaseRepository(roomDatabase: RoomDatabase): DataBaseRepository {
        return DataBaseRepositoryImpl(roomDatabase)
    }

}