package com.codezfox.exchangeratesmvp.ui.bankbranch

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate
import com.codezfox.exchangeratesmvp.data.models.Service
import com.codezfox.exchangeratesmvp.ui.DateUpdate
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmViewModel
import com.codezfox.exchangeratesmvp.ui.base.NetworkManager
import com.codezfox.exchangeratesmvp.ui.base.StringResources.Companion.create
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.terrakok.cicerone.Router
import java.util.*

class BankBranchViewModel(
    private val bank: Bank,
    private val branch: Branch,
    private val networkManager: NetworkManager,
    private val interactor: BankBranchInteractor
) : BaseMvvmViewModel<Router>() {

    val title = ObservableField<String>(bank.name)
    val items = ObservableField<List<DisplayableItem>>()

    private var allServices = emptyList<Service>()
    private val servicesRelay = PublishSubject.create<List<Service>>()
    val showServices = servicesRelay.hide()
    
    val isRefresh = ObservableBoolean(false)
    val isError = ObservableBoolean(false)

    private val loadingDisposable = SerialDisposable()

    init {

        interactor.getCurrencyRates(branch)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (bank, exchangeRates, date) ->
                showBranch(bank, branch, exchangeRates, date)
            }, {
                it.printStackTrace()
            })
            .addDisposable()

        loadCurrencyRates()
        loadServices()
    }

    private fun showBranch(bank: Bank, branch: Branch, exchangeRates: List<CurrencyExchangeRate>, date: Date?) {
        val items = mutableListOf<DisplayableItem>()

        if (bank.name != branch.name) {
            items.add(DataString(create(branch.name), DataString.TYPE.STRING))
        }

        items.add(DataString(create(branch.address), DataString.TYPE.ADDRESS))
        items.addAll(branch.getPhonesList().map { DataString(create(it), DataString.TYPE.PHONE) })

        if (!branch.services.isNullOrEmpty()) {
            items.add(DataString(create(R.string.services), DataString.TYPE.SERVICES))
        }

        if (date != null && isError.get()) {
            items.add(DateUpdate(date))
        }

        if (exchangeRates.isNotEmpty()) {
            items.add(CurrencyRatesHeader("Валюта"))
            items.addAll(exchangeRates)
        }

        this.items.set(items)
    }

    fun loadCurrencyRates() {
        isRefresh.set(true)
        interactor.loadCurrencyRates(branch)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isError.set(false)
                isRefresh.set(false)
            }, {
                isError.set(true)
                isRefresh.set(false)
                it.printStackTrace()
            })
            .addDisposable(loadingDisposable)
    }

    private fun loadServices() {
        interactor.getServices()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                this.allServices = it
            }, {
                it.printStackTrace()
            })
            .addDisposable()
    }


    fun onBackPressed() {
        router.exit()
    }

    fun openServices() {
        val servicesIds = branch.services
        if (!servicesIds.isNullOrEmpty()) {
            val services = this.allServices.filter { servicesIds.contains(it.id) }
            servicesRelay.onNext(services)
        } else {
            interactor.getServices()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ allServices ->
                    this.allServices = allServices
                    openServices()
                }, {
                    //todo error ui
                    it.printStackTrace()
                })
                .addDisposable()
        }
    }

}