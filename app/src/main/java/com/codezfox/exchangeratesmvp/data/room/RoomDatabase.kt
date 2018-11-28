package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.Rate
import com.codezfox.exchangeratesmvp.domain.models.RateBank


@Database(entities = [Currency::class, Rate::class, RateBank::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomDatabase : android.arch.persistence.room.RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun rateDao(): RateDao

    abstract fun rateBankDao(): RateBankDao

}

