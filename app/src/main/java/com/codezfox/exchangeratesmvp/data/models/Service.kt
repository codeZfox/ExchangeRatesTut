package com.codezfox.exchangeratesmvp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Service(
        @PrimaryKey
        val id: String,
        val name: String
)