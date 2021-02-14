package com.codezfox.exchangeratesmvp.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ObservableField
import com.codezfox.exchangeratesmvp.data.repositories.preferences.PreferencesRepository
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmViewModel
import io.reactivex.subjects.PublishSubject
import ru.terrakok.cicerone.Router

class SettingsViewModel(
        private val preferencesRepository: PreferencesRepository
) : BaseMvvmViewModel<Router>() {

    val currentTheme = ObservableField(NightModeType.fromValue(preferencesRepository.getSavedNightMode()))
    val actionSelectThemePublish = PublishSubject.create<NightModeType>() //todo refactoring

    init {

    }

    fun onBackPressed() {
        router.exit()
    }

    fun changeTheme() {
        actionSelectThemePublish.onNext(currentTheme.get()!! )
    }

    fun applyTheme(nightMode: NightModeType) {
        currentTheme.set(nightMode)
        preferencesRepository.saveNightMode(nightMode.value)
        AppCompatDelegate.setDefaultNightMode(nightMode.value)
    }
}

