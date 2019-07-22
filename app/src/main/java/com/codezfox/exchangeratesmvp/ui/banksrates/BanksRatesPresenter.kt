package com.codezfox.exchangeratesmvp.ui.banksrates

import com.arellomobile.mvp.InjectViewState
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.RateBank
import com.codezfox.paginator.NetworkManager
import com.codezfox.paginator.screen.MvpPaginatorPresenter
import com.codezfox.paginator.screen.PageContent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class BanksRatesPresenter(

        private val currency: Currency,
        private val interactor: BanksRatesInteractor,
        private val networkManager: NetworkManager,
        private var router: Router

) : MvpPaginatorPresenter<RateBank, BanksRatesView>() {

    private var sort = RateCurrencySort.BUY

    private var list: List<RateBank> = emptyList()

    override fun requestFactory(page: Int): Single<PageContent<RateBank>> {
        return interactor.loadBanksRates(currency, sort)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { (list, date, _) ->
                    viewState.showLastDateUpdated(date)
                    this.list = list
                }
                .observeOn(Schedulers.io())
                .map { (it, _, isOfflineSource) ->
                    PageContent(it, true, isOfflineSource)
                }
    }

    override fun onFirstViewAttach() {
        subscribeToNetworkConnected(networkManager)
        viewState.showSortType(this.sort)
        viewState.showCurrencyInfo(currency)
        pagination.refresh()
    }


    fun changeSort(sort: RateCurrencySort) {
        val sortedList = interactor.sortBanksRates(list, sort)
        pagination.setAllData(sortedList, true)
        viewState.showSortType(sort)
        this.sort = sort
    }

    fun onBackPressed() {
        router.exit()
    }

    fun openBankExchangeRates(bank: Bank) {
        router.navigateTo(Screens.ExchangeRateCurrencyOfBranchesBank(bank, currency))
    }

}
