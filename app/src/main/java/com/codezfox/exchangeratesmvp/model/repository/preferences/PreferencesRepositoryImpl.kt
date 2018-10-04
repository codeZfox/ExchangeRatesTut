package com.codezfox.exchangeratesmvp.model.repository.preferences

import android.content.Context

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {

    companion object {
        const val PREFERENCES_TAG = "EXCHANGERATES"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE)

}