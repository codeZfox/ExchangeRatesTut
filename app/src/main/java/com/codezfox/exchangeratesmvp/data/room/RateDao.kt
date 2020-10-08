package com.codezfox.exchangeratesmvp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codezfox.exchangeratesmvp.data.models.BestRate
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import io.reactivex.Flowable

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBestRates(rate: List<BestRate>)


    @Query("DELETE FROM BestRate")
    fun deleteBestRates()

    @Query("SELECT BestRate.*, Currency.* FROM BestRate INNER JOIN Currency ON BestRate.currencyCode = Currency.id")
    fun getBestRatesCurrencies(): Flowable<List<BestRateCurrency>>

}