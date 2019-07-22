package com.codezfox.exchangeratesmvp.ui.currencyrates

import com.arellomobile.mvp.InjectViewState
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.paginator.NetworkManager
import com.codezfox.paginator.screen.PageContent
import com.codezfox.paginator.screen.IMvpPaginatorPresenter
import com.codezfox.paginator.screen.MvpPaginatorPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class CurrencyRatesPresenter(
        private val router: Router,
        private val interactor: CurrencyRatesInteractor,
        private val networkManager: NetworkManager
) : MvpPaginatorPresenter<BestRateCurrency, CurrencyRatesView>(), IMvpPaginatorPresenter<BestRateCurrency, CurrencyRatesView> {

    override fun requestFactory(page: Int): Single<PageContent<BestRateCurrency>> {
        return interactor.loadRates()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { (_, date) ->
                    viewState.showLastDateUpdated(date)
                }
                .observeOn(Schedulers.io())
                .map { (it, _, isOfflineSource) ->
                    PageContent(it, true, isOfflineSource)
                }
    }

    override fun onFirstViewAttach() {
        subscribeToNetworkConnected(networkManager)
        pagination.refresh()
    }

    fun openCurrency(rateCurrency: BestRateCurrency) {
        router.navigateTo(Screens.ExchangeRateCurrencyOfBanks(rateCurrency.currency))
    }

}
