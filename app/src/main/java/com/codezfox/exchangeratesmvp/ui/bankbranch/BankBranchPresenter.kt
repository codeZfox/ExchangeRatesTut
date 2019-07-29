package com.codezfox.exchangeratesmvp.ui.bankbranch

import com.arellomobile.mvp.InjectViewState
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate
import com.codezfox.exchangeratesmvp.data.models.Service
import com.codezfox.exchangeratesmvp.ui._base.BasePresenter
import com.codezfox.paginator.NetworkManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import ru.terrakok.cicerone.Router
import java.util.*

@InjectViewState
class BankBranchPresenter(
        private val bank: Bank,
        private val branch: Branch,
        private val router: Router,
        private val networkManager: NetworkManager,
        private val interactor: BankBranchInteractor) : BasePresenter<BankBranchView>() {

    private var services = emptyList<Service>()

    private val subjectBranch: Subject<Triple<Bank, List<CurrencyExchangeRate>, Date?>> = BehaviorSubject.createDefault(Triple(bank, emptyList(), null))

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showTitle(bank)

        subjectBranch.subscribe { (bank, exchangeRates, date) ->
            viewState.showBranch(bank, branch, exchangeRates, date)
            viewState.hideLoading()
        }.also {
            disposable.add(it)
        }

        refresh()
        loadServices()
    }

    private var loading: Disposable? = null

    fun refresh() {
        viewState.showLoading()
        loading?.dispose()
        loading = loadData(networkManager.getNetworkConnected(),
                interactor.getCurrencyRates(branch).toObservable(),
                subjectBranch)
    }

    private fun loadServices() {
        disposable.add(interactor.getServices()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    this.services = it
                }, {
                    it.printStackTrace()
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        loading?.dispose()
        loading = null
    }

    fun onBackPressed() {
        router.exit()
    }

    fun openServices() {
        val servicesIds = branch.services
        if (!servicesIds.isNullOrEmpty()) {
            val services = this.services.filter { servicesIds.contains(it.id) }
            viewState.openServices(services)
        } else {
            disposable.add(interactor.getServices()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        this.services = it
                        if (!servicesIds.isNullOrEmpty()) {
                            val services = this.services.filter { servicesIds.contains(it.id) }
                            viewState.openServices(services)
                        }
                    }, {
                        it.printStackTrace()
                    }))
        }
    }

}