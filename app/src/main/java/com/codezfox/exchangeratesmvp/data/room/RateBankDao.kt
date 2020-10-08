package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.data.models.BankRate
import io.reactivex.Flowable

@Dao
interface RateBankDao {

    @Query("SELECT * FROM BankRate WHERE fromCurrency == :currency")
    fun getBankRates(currency: String): Flowable<List<BankRate>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBankRates(rate: List<BankRate>)


    @Query("DELETE FROM BankRate")
    fun deleteRates()

    @Query("SELECT * FROM BankRate WHERE bankId == :bankId")
    fun getBankById(bankId: String): Flowable<BankRate>

}