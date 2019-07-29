package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.*
import com.codezfox.exchangeratesmvp.data.models.*
import io.reactivex.Single

@Dao
interface BranchDao {

    @Query("SELECT * FROM Branch")
    fun getBranches(): List<Branch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBranches(list: List<Branch>)

    @Update
    fun updateBranches(list: List<Branch>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchangeRates(list: List<ExchangeRate>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchangeRateBranch(list: List<ExchangeRateBranch>)

    @Query("""SELECT Branch.*, ExchangeRate.* FROM Branch INNER JOIN ExchangeRate ON Branch.id = ExchangeRate.branche_id WHERE Branch.bank_id = :bankId AND ExchangeRate.foreignCurrencyId = :fromCurrency AND ExchangeRate.nationalCurrencyId = :toCurrency AND Branch.isOpened = '1'""")
    fun getBranchesCurrencies(bankId: String, fromCurrency: String, toCurrency: String): Single<List<BranchWithExchangeRate>>

    @Query("""SELECT ExchangeRateBranch.*,  Currency.* FROM ExchangeRateBranch INNER JOIN Currency ON ExchangeRateBranch.foreignCurrencyId = Currency.id WHERE ExchangeRateBranch.branche_id = :branchId""")
    fun getCurrencyExchangeRate(branchId: String): Single<List<CurrencyExchangeRate>>


}