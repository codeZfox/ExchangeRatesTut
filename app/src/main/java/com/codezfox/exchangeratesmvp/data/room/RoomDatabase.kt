package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.codezfox.exchangeratesmvp.data.models.*


@Database(entities = [Currency::class, BestRate::class, BankRate::class, Branch::class, ExchangeRate::class, Service::class, ExchangeRateBranch::class], version = 6)
@TypeConverters(Converters::class)
abstract class RoomDatabase : android.arch.persistence.room.RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun rateDao(): RateDao

    abstract fun rateBankDao(): RateBankDao

    abstract fun branchDao(): BranchDao

    abstract fun serviceDao(): ServiceDao

}

