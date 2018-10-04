package com.codezfox.exchangeratesmvp.presentation.currencyrates

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.extensions.launchUIR
import com.codezfox.exchangeratesmvp.extensions.showMessage
import com.codezfox.exchangeratesmvp.model.interactor.currencyrates.CurrencyRatesInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CurrencyRatesPresenter : MvpPresenter<CurrencyRatesView>() {

    @Inject
    lateinit var router: Router

    private var interactor = CurrencyRatesInteractor()

    init {
        DaggerUtils.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        launchUIR({
            interactor.loadCurrencyRates()
        }, {
            viewState.showRates(it)
        }, {
            viewState.showMessage(it.localizedMessage)
        })
    }
}
