package com.codezfox.exchangeratesmvp.presentation.presenter.currencyrates

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.codezfox.exchangeratesmvp.presentation.Screens
import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.extensions.launchUIR
import com.codezfox.exchangeratesmvp.extensions.showMessage
import com.codezfox.exchangeratesmvp.domain.currencyrates.CurrencyRatesInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CurrencyRatesPresenter : MvpPresenter<CurrencyRatesView>() {

    @Inject
    lateinit var router: Router

    private var interactor = CurrencyRatesInteractor()

    var list: List<RateCurrency> = listOf()

    init {
        DaggerUtils.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showShimmerEffect(true)
        loadRates()
    }

    fun loadRates() {

        launchUIR({
            interactor.loadCurrencyRates()
        }, { (list, date) ->

            this.list = list

            viewState.showRates(list)

            viewState.showProgress(false)
            viewState.showShimmerEffect(false)
            viewState.hideEmptyText()
            viewState.showLastDateUpdated(date)


        }, {
            it.printStackTrace()

//            loadLocalRates()
            viewState.showLastDateUpdated(null)

            viewState.showProgress(false)
            viewState.showShimmerEffect(false)

            if (list.isEmpty()) {
                viewState.showEmptyText(it.toString())
            } else {
                viewState.hideEmptyText()
                viewState.showMessage(it.toString())
            }
        })
    }

    fun openCurrency(rateCurrency: RateCurrency) {
        router.navigateTo(Screens.CURRENCY_BANKS, rateCurrency.currency!!)
    }

}
