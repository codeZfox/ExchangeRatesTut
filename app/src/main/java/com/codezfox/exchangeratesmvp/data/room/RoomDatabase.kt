package com.codezfox.exchangeratesmvp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codezfox.exchangeratesmvp.data.models.*


@Database(entities = [Currency::class, BestRate::class, BankRate::class, Branch::class, ExchangeRate::class, Service::class, ExchangeRateBranch::class], version = 7)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {

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