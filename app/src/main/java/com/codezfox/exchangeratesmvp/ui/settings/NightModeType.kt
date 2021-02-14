package com.codezfox.exchangeratesmvp.ui.settings

import android.os.Build
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import com.codezfox.exchangeratesmvp.R

enum class NightModeType(
        val customOrdinal: Int,
        @AppCompatDelegate.NightMode val value: Int,
        @StringRes val title: Int
) {

    MODE_NIGHT_NO(
            0,
            AppCompatDelegate.MODE_NIGHT_NO,
            R.string.mode_night_no
    ),

    MODE_NIGHT_YES(
            1,
            AppCompatDelegate.MODE_NIGHT_YES,
            R.string.mode_night_yes
    ),

    MODE_NIGHT_FOLLOW_SYSTEM(
            2,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            R.string.mode_night_follow_system
    ),

    MODE_NIGHT_AUTO_BATTERY(
            2,
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY,
            R.string.mode_night_auto_battery
    );

    companion object {

        fun fromValue(@AppCompatDelegate.NightMode value: Int) = values().firstOrNull { it.value == value }
                ?: getDefaultMode()

        fun fromCustomOrdinal(ordinal: Int): NightModeType {
            return if (ordinal == 2) {
                getDefaultMode()
            } else {
                values().firstOrNull { it.customOrdinal == ordinal } ?: getDefaultMode()
            }
        }

        fun getDefaultMode(): NightModeType {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MODE_NIGHT_FOLLOW_SYSTEM
            } else {
                MODE_NIGHT_AUTO_BATTERY
            }
        }

        fun listUI() = listOf(MODE_NIGHT_NO, MODE_NIGHT_YES, getDefaultMode())
    }
}