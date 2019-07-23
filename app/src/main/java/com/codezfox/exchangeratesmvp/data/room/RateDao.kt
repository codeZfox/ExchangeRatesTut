package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.data.models.BestRate
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.data.models.Currency
import io.reactivex.Single

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBestRates(rate: List<BestRate>)


    @Query("DELETE FROM BestRate")
    fun deleteBestRates()

    @Query("SELECT BestRate.*, Currency.* FROM BestRate INNER JOIN Currency ON BestRate.currencyCode = Currency.id")
    fun getBestRatesCurrencies(): Single<List<BestRateCurrency>>

}