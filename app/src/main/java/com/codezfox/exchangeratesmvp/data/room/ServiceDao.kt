package com.codezfox.exchangeratesmvp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codezfox.exchangeratesmvp.data.models.Service

@Dao
interface ServiceDao {

    @Insert
    fun insertService(list: List<Service>)


    @Query("DELETE FROM Service")
    fun deleteServices()


}