package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.domain.models.RateBank

@Dao
interface RateBankDao {

    @Query("SELECT * FROM RateBank WHERE fromCurrency == :currency")
    fun getRates(currency: String): List<RateBank>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rate: List<RateBank>)


    @Query("DELETE FROM RateBank")
    fun deleteRates()


}