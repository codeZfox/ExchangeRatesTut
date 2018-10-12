package com.codezfox.exchangeratesmvp.model.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateCurrency

@Dao
interface RateDao {

    @Query("SELECT * FROM Rate")
    fun getRates(): List<Rate>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rate: List<Rate>)


    @Query("DELETE FROM Rate")
    fun deleteRates()

    @Query("SELECT Rate.*, Currency.* FROM Rate INNER JOIN Currency ON Rate.currencyCode = Currency.id")
    fun getRatess(): List<RateCurrency>


}