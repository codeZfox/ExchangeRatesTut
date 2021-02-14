package com.codezfox.exchangeratesmvp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.databinding.ScreenSettingsBinding
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmFragment
import com.codezfox.exchangeratesmvp.ui.base.viewModelLazyInstance
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.screen_converter.*
import ru.terrakok.cicerone.Router

class SettingsFragment : BaseMvvmFragment<SettingsViewModel, Router>() {

    override val viewModel: SettingsViewModel by viewModelLazyInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ScreenSettingsBinding.inflate(inflater).also { binding ->
            binding.viewModel = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }//todo maybe
        viewModel.actionSelectThemePublish
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createNightModeChooserDialog(it).show()
                }, {
                    it.printStackTrace()
                })
                .addDisposable()
    }

    private fun createNightModeChooserDialog(currentTheme: NightModeType): AlertDialog {
        return MaterialAlertDialogBuilder(requireContext())
                .apply {
                    setTitle(getString(R.string.item_dark_theme_text_view_title_text))

                    val nightModes = NightModeType.listUI().map { getString(it.title) }.toTypedArray()

                    setSingleChoiceItems(nightModes, currentTheme.customOrdinal) { dialog, which ->

                        val nightMode = NightModeType.fromCustomOrdinal(which)

                        viewModel.applyTheme(nightMode)

                        dialog.dismiss()

                    }
                    setNegativeButton(getString(R.string.cancel), null)
                }
                .create()
    }

}