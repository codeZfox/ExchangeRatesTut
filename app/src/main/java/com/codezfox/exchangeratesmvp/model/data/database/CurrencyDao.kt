package com.codezfox.exchangeratesmvp.model.data.database

import android.arch.persistence.room.*
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


