package com.codezfox.exchangeratesmvp.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class Service(
        @PrimaryKey
        val id: String,
        val name: String
)