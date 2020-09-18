package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.migration.Migration
import com.codezfox.exchangeratesmvp.data.models.*


@Database(entities = [Currency::class, BestRate::class, BankRate::class, Branch::class, ExchangeRate::class, Service::class, ExchangeRateBranch::class], version = 7)
@TypeConverters(Converters::class)
abstract class RoomDatabase : android.arch.persistence.room.RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun rateDao(): RateDao

    abstract fun rateBankDao(): RateBankDao

    abstract fun branchDao(): BranchDao

    abstract fun serviceDao(): ServiceDao

}

val MIGRATION_6_7: Migration = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE `currency` ADD COLUMN `order` INTEGER DEFAULT 0 NOT NULL")
    }
}