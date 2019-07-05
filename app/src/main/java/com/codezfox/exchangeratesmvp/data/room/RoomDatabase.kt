package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.codezfox.exchangeratesmvp.data.models.*


@Database(entities = [Currency::class, Rate::class, RateBank::class, Branch::class, ExchangeRate::class], version = 4)
@TypeConverters(Converters::class)
abstract class RoomDatabase : android.arch.persistence.room.RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun rateDao(): RateDao

    abstract fun rateBankDao(): RateBankDao

    abstract fun branchDao(): BranchDao

}

