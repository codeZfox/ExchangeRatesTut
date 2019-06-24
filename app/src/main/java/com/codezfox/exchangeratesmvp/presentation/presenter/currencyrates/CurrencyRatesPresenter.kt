package com.codezfox.exchangeratesmvp.presentation.presenter.currencyrates

import com.arellomobile.mvp.InjectViewState
import com.codezfox.exchangeratesmvp.domain.currencyrates.CurrencyRatesInteractor
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.presentation.Screens
import com.codezfox.paginator.NetworkManager
import com.codezfox.paginator.screen.IMvpPaginatorPresenter
import com.codezfox.paginator.screen.MvpPaginatorPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class CurrencyRatesPresenter(
        private val router: Router,
        private val interactor: CurrencyRatesInteractor,
        private val networkManager: NetworkManager
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
        subscribeToNetworkConnected(networkManager)
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
