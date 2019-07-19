package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.data.models.BestRate
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import io.reactivex.Single

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rate: List<BestRate>)


    @Query("DELETE FROM BestRate")
    fun deleteRates()

    @Query("SELECT BestRate.*, Currency.* FROM BestRate INNER JOIN Currency ON BestRate.currencyCode = Currency.id")
    fun getRateCurrencies(): Single<List<BestRateCurrency>>

}