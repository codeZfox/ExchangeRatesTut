package com.codezfox.exchangeratesmvp.ui.bestrates

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.data.config.Group
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.extensions.isNetworkException
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmViewModel
import com.codezfox.exchangeratesmvp.ui.base.NetworkManager
import com.codezfox.exchangeratesmvp.ui.base.StringResources
import com.codezfox.exchangeratesmvp.ui.base.create
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import ru.terrakok.cicerone.Router
import java.util.*

class BestRatesViewModel(
    private val interactor: BestRatesInteractor,
    private val networkManager: NetworkManager
) : BaseMvvmViewModel<Router>() {

    val items = ObservableField<List<BestRateCurrency>>()
    val isRefresh = ObservableBoolean(false)
    val empty = ObservableField<EmptyData>()
    val error = ObservableField<ErrorData>()
    val lastDateUpdated = ObservableField<Date?>()
    val isVisibleLastDateUpdated = ObservableBoolean()

    private val loadingDisposable = SerialDisposable()

    init {
        interactor.getRates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (it, date) ->
                if (it.isEmpty()) {
                    empty.set(EmptyData())
                } else {
                    empty.set(null)
                }
                items.set(it)
                lastDateUpdated.set(date)
            }, {
                empty.set(null)
                it.printStackTrace()
            })
            .addDisposable()

        loadRates()
    }

    private fun loadRates() {
        val load = if (Group.BestRates().isNBData.value) interactor.loadNBRates() else interactor.loadRates()

        load
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

    fun openCurrency(rateCurrency: BestRateCurrency) {
        if (Group.BestRates().isEnableCurrencyScreen.value) {
            router.navigateTo(Screens.ExchangeRateCurrencyOfBanks(rateCurrency.currency))
        }
    }

    fun openSettings() {
        router.navigateTo(Screens.Settings())
    }

}
