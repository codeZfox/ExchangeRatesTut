package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.RateBank
import io.reactivex.Single

@Dao
interface RateBankDao {

    @Query("SELECT * FROM RateBank WHERE fromCurrency == :currency")
    fun getRates(currency: String): Single<List<RateBank>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rate: List<RateBank>)


    @Query("DELETE FROM RateBank")
    fun deleteRates()

    @Query("SELECT * FROM RateBank WHERE bankId == :bankId")
    fun getBankById(bankId: String): Single<RateBank>

}