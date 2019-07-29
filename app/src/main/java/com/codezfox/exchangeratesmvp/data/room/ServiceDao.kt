package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.codezfox.exchangeratesmvp.data.models.Service

@Dao
interface ServiceDao {

    @Insert
    fun insertService(list: List<Service>)


    @Query("DELETE FROM Service")
    fun deleteServices()


}