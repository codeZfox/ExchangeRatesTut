package com.codezfox.exchangeratesmvp.ui.ercofbranchesbank

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.BranchWithExchangeRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmViewModel
import com.codezfox.exchangeratesmvp.ui.base.NetworkManager
import com.codezfox.exchangeratesmvp.ui.base.toFlowable
import com.codezfox.exchangeratesmvp.ui.bestrates.EmptyData
import com.codezfox.exchangeratesmvp.ui.bestrates.ErrorData
import com.codezfox.exchangeratesmvp.ui.bestrates.RetryListener
import com.codezfox.exchangeratesmvp.ui.ercofbanks.RateCurrencySort
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import ru.terrakok.cicerone.Router
import java.util.*

class ExchangeRateCurrencyOfBranchesBankViewModel(

    private val currency: Currency,
    private val bank: Bank,
    private val interactor: ExchangeRateCurrencyOfBranchesBankInteractor,
    private val networkManager: NetworkManager

) : BaseMvvmViewModel<Router>() {


    val title = ObservableField<String>(bank.name)
    val subTitle = ObservableField<String>(currency.name)

    var sort = ObservableField(RateCurrencySort.BUY)

    val items = ObservableField<List<BranchWithExchangeRate>>()
    val isRefresh = ObservableBoolean(false)
    val empty = ObservableField<EmptyData>()
    val error = ObservableField<ErrorData>()
    val lastDateUpdated = ObservableField<Date?>()
    val isVisibleLastDateUpdated = ObservableBoolean()

    private val loadingDisposable = SerialDisposable()

    private val toCurrency = Currency("BYN", "", "", "", "", "", "", "", "", 0, 0)

    init {
        sort.toFlowable(BackpressureStrategy.LATEST)
            .distinctUntilChanged()
            .switchMap { sort ->
                interactor.getBanksRates(bank, currency, toCurrency, sort)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (it, date) ->
                if (it.isEmpty()) {
                    empty.set(EmptyData())
                } else {
                    empty.set(null)
                }
                items.set(it)
                lastDateUpdated.set(date) //todo date to ui in item
            }, {
                empty.set(null)
                it.printStackTrace()
            })
            .addDisposable()

        loadRates()
    }

    private fun loadRates() {
        interactor.loadBanksRates(bank, currency, toCurrency)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                error.set(null)
                isVisibleLastDateUpdated.set(false)
                isRefresh.set(false)
            }, { throwable ->
                error.set(ErrorData(description = throwable?.message
                    ?: throwable.toString(), onClick = object : RetryListener {
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

    fun openBankBranch(branch: Branch) {
        router.navigateTo(Screens.BankBranch(bank, branch))
    }

}
