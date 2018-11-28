package com.codezfox.exchangeratesmvp.presentation.presenter.banksrates

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.codezfox.exchangeratesmvp.di.DaggerUtils
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.extensions.launchUIR
import com.codezfox.exchangeratesmvp.extensions.showMessage
import com.codezfox.exchangeratesmvp.domain.banksrates.BanksRatesInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class BanksRatesPresenter(

        private val currency: Currency

) : MvpPresenter<BanksRatesView>() {

    @Inject
    lateinit var router: Router

    private var interactor = BanksRatesInteractor(currency)

    var list: List<RateBank> = listOf()

    init {
        DaggerUtils.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        viewState.showShimmerEffect(true)
        viewState.showCurrencyInfo(currency)
        loadRates()
    }

    fun loadRates() {

        launchUIR({

            interactor.loadBanksRates()

        }, { list ->

            this.list = list

            viewState.showRates(list)

            viewState.showProgress(false)
            viewState.showShimmerEffect(false)
            viewState.hideEmptyText()


        }, {
            it.printStackTrace()

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

    fun onBackPressed() {
        router.exit()
    }

}
