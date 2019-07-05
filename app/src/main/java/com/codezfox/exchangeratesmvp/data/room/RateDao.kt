package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.data.models.Rate
import com.codezfox.exchangeratesmvp.data.models.RateCurrency
import io.reactivex.Single

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rate: List<Rate>)


    @Query("DELETE FROM Rate")
    fun deleteRates()

    @Query("SELECT Rate.*, Currency.* FROM Rate INNER JOIN Currency ON Rate.currencyCode = Currency.id")
    fun getRateCurrencies(): Single<List<RateCurrency>>

}