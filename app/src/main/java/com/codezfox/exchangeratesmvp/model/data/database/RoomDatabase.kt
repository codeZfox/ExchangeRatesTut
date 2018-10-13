package com.codezfox.exchangeratesmvp.model.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateBank


@Database(entities = [Currency::class, Rate::class, RateBank::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomDatabase : android.arch.persistence.room.RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun rateDao(): RateDao

    abstract fun rateBankDao(): RateBankDao

}

