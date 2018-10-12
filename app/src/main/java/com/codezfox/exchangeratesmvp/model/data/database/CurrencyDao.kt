package com.codezfox.exchangeratesmvp.model.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.codezfox.exchangeratesmvp.entity.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM Currency")
    fun getCurrencies(): List<Currency>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(currency: List<Currency>)

    @Update
    fun updateCurrency(currency: Currency): Int


    @Query("DELETE FROM Currency")
    fun deleteCurrencies()


}


