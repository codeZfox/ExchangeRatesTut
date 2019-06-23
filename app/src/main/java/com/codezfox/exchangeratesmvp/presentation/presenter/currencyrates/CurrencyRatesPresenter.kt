package com.codezfox.exchangeratesmvp.presentation.presenter.currencyrates

import com.arellomobile.mvp.InjectViewState
import com.codezfox.exchangeratesmvp.data.repositories.SystemRepository
import com.codezfox.exchangeratesmvp.domain.currencyrates.CurrencyRatesInteractor
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.presentation.Screens
import com.codezfox.exchangeratesmvp.presentation.paginator.screen.IMvpPaginatorPresenter
import com.codezfox.exchangeratesmvp.presentation.paginator.screen.MvpPaginatorPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class CurrencyRatesPresenter(
        private val router: Router,
        private val interactor: CurrencyRatesInteractor,
        private val systemRepository: SystemRepository
) : MvpPaginatorPresenter<RateCurrency, CurrencyRatesView>(), IMvpPaginatorPresenter<RateCurrency, CurrencyRatesView> {

    override fun requestFactory(page: Int): Single<List<RateCurrency>> {
        return interactor.loadRates()
                .map {
                    if (page == 1) {
                        it
                    } else {
                        emptyList()
                    }
                }
    }

    override fun onFirstViewAttach() {
        subscribeToNetworkConnected(systemRepository)
        pagination.refresh()

        disposable.add(interactor.subjectDate
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showLastDateUpdated(it.get())
                }, {
                    it.printStackTrace()
                }))
    }

    fun openCurrency(rateCurrency: RateCurrency) {
        router.navigateTo(Screens.BanksRates(rateCurrency.currency))
    }

}
