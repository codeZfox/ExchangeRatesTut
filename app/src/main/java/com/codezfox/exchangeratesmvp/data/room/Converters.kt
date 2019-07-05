package com.codezfox.exchangeratesmvp.data.room

import android.arch.persistence.room.TypeConverter
import com.codezfox.exchangeratesmvp.data.models.Schedule
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.getTime()
    }

    @TypeConverter
    fun fromScheduleList(list: List<Schedule>?): String? {
        if (list == null) {
            return null
        }
        val type = object : TypeToken<List<Schedule>>() {

        }.getType()
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toScheduleList(string: String?): List<Schedule>? {
        if (string == null) {
            return null
        }
        val type = object : TypeToken<List<Schedule>>() {

        }.getType()
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        val type = object : TypeToken<List<String>>() {

        }.getType()
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toStringList(string: String?): List<String>? {
        if (string == null) {
            return null
        }
        val type = object : TypeToken<List<String>>() {

        }.getType()
        return Gson().fromJson(string, type)
    }
}
