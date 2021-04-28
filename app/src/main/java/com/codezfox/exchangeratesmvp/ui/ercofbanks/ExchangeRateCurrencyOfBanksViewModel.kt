package com.codezfox.exchangeratesmvp.ui.ercofbanks

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.extensions.isNetworkException
import com.codezfox.exchangeratesmvp.ui.base.*
import com.codezfox.exchangeratesmvp.ui.bestrates.EmptyData
import com.codezfox.exchangeratesmvp.ui.bestrates.ErrorData
import com.codezfox.exchangeratesmvp.ui.bestrates.RetryListener
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import ru.terrakok.cicerone.Router
import java.util.*

class ExchangeRateCurrencyOfBanksViewModel(

        private val currency: Currency,
        private val interactor: ExchangeRateCurrencyOfBanksInteractor,
        private val networkManager: NetworkManager

) : BaseMvvmViewModel<Router>() {

    val title = ObservableField<String>(currency.name)

    var sort = ObservableField(RateCurrencySort.BUY)

    val items = ObservableField<List<BankRate>>()
    val isRefresh = ObservableBoolean(false)
    val empty = ObservableField<EmptyData>()
    val error = ObservableField<ErrorData>()
    val lastDateUpdated = ObservableField<Date?>()
    val isVisibleLastDateUpdated = ObservableBoolean()

    private val loadingDisposable = SerialDisposable()

    init {
        sort.toFlowable(BackpressureStrategy.LATEST)
                .distinctUntilChanged()
                .switchMap { sort ->
                    interactor.getBanksRates(currency, sort)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (list, date) ->
                    lastDateUpdated.set(date)
                    if (list.isEmpty()) {
                        empty.set(EmptyData())
                    } else {
                        empty.set(null)
                    }
                    items.set(list)
                }, {
                    empty.set(null)
                    it.printStackTrace()
                })
                .addDisposable()

        loadRates()
    }

    private fun loadRates() {
        interactor.loadBanksRates(currency)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    error.set(null)
                    isVisibleLastDateUpdated.set(false)
                    isRefresh.set(false)
                }, { throwable ->
                    val message = if (throwable.isNetworkException()) {
                        StringResources.create(R.string.error_connection_issues)
                    } else {
                        throwable?.message?.create() ?: throwable.toString().create()
                    }
                    error.set(ErrorData(description = message, onClick = object : RetryListener {
                        override fun invoke() {
                            reload()
                        }
                    }))
                    isVisibleLastDateUpdated.set(true)
                    isRefresh.set(false)
                    throwable.printStackTrace()
                })
                .addDisposable(loadingDisposable)
    }

    fun reload() {
        isRefresh.set(true)
        loadRates()
    }


    fun changeSort(sort: RateCurrencySort) {
        this.sort.set(sort)
    }

    fun onBackPressed() {
        router.exit()
    }

    fun openBankExchangeRates(bank: Bank) {
        router.navigateTo(Screens.ExchangeRateCurrencyOfBranchesBank(bank, currency))
    }

}
