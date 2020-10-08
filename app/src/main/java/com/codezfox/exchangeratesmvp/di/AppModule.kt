package com.codezfox.exchangeratesmvp.di

import android.arch.persistence.room.Room
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepository
import com.codezfox.exchangeratesmvp.data.repositories.database.DatabaseRepositoryImpl
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepositoryImpl
import com.codezfox.exchangeratesmvp.data.room.MIGRATION_6_7
import com.codezfox.exchangeratesmvp.data.room.RoomDatabase
import com.codezfox.exchangeratesmvp.ui.bankbranch.BankBranchInteractor
import com.codezfox.exchangeratesmvp.ui.bankbranch.BankBranchViewModel
import com.codezfox.exchangeratesmvp.ui.base.NetworkManager
import com.codezfox.exchangeratesmvp.ui.bestrates.BestRatesInteractor
import com.codezfox.exchangeratesmvp.ui.bestrates.BestRatesViewModel
import com.codezfox.exchangeratesmvp.ui.converter.ConverterInteractor
import com.codezfox.exchangeratesmvp.ui.converter.ConverterViewModel
import com.codezfox.exchangeratesmvp.ui.ercofbanks.ExchangeRateCurrencyOfBanksInteractor
import com.codezfox.exchangeratesmvp.ui.ercofbanks.ExchangeRateCurrencyOfBanksViewModel
import com.codezfox.exchangeratesmvp.ui.ercofbranchesbank.ExchangeRateCurrencyOfBranchesBankInteractor
import com.codezfox.exchangeratesmvp.ui.ercofbranchesbank.ExchangeRateCurrencyOfBranchesBankViewModel
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
        Room.databaseBuilder(instance(), RoomDatabase::class.java, "exchangerates.db")
            .addMigrations(MIGRATION_6_7)
            .allowMainThreadQueries()
            .build()
    }

    bind<DatabaseRepository>() with singleton { DatabaseRepositoryImpl(instance()) }

    bind<NetworkManager>() with singleton { NetworkManager(instance()) }

    bind<BestRatesViewModel>() with provider { BestRatesViewModel(BestRatesInteractor(instance(), instance(), instance()), instance()) }

    bind<ExchangeRateCurrencyOfBanksViewModel>() with factory { currency: Currency -> ExchangeRateCurrencyOfBanksViewModel(currency, ExchangeRateCurrencyOfBanksInteractor(instance(), instance(), instance()), instance()) }

    bind<ExchangeRateCurrencyOfBranchesBankViewModel>() with factory { currency: Currency, bank: Bank -> ExchangeRateCurrencyOfBranchesBankViewModel(currency, bank, ExchangeRateCurrencyOfBranchesBankInteractor(instance(), instance(), instance()), instance()) }

    bind<BankBranchViewModel>() with factory { bank: Bank, branch: Branch -> BankBranchViewModel(bank, branch, instance(), BankBranchInteractor(instance(), instance(), instance())) }

    bind<ConverterViewModel>() with provider { ConverterViewModel(ConverterInteractor(instance(), instance())) }


}