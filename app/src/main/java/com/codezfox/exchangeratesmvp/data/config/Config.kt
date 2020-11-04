package com.codezfox.exchangeratesmvp.data.config

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

sealed class Config<T>(val key: String) {

    abstract val value: T
    abstract val default: T
    abstract fun set(value: T)

    class BooleanConfig(key: String, override val default: Boolean = true) : Config<Boolean>(key) {

        override val value = Firebase.remoteConfig.getBoolean(key)

        override fun set(value: Boolean) {
            Firebase.remoteConfig.setDefaultsAsync(mapOf(key to value))
        }
    }

    class StringConfig(key: String, override val default: String) : Config<String>(key) {

        override val value = Firebase.remoteConfig.getString(key)

        override fun set(value: String) {
            Firebase.remoteConfig.setDefaultsAsync(mapOf(key to value))
        }
    }

    class LongConfig(key: String, override val default: Long) : Config<Long>(key) {

        override val value = Firebase.remoteConfig.getLong(key)

        override fun set(value: Long) {
            Firebase.remoteConfig.setDefaultsAsync(mapOf(key to value))
        }
    }

    class DoubleConfig(key: String, override val default: Double) : Config<Double>(key) {

        override val value = Firebase.remoteConfig.getDouble(key)

        override fun set(value: Double) {
            Firebase.remoteConfig.setDefaultsAsync(mapOf(key to value))
        }
    }

}

