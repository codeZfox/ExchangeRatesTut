package com.codezfox.exchangeratesmvp.ui.branch

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class BranchPresenter(
        private val bank: Bank?,
        private val branch: Branch,
        private val router: Router,
        private val interactor: BranchInteractor) : MvpPresenter<BranchView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showTitle(bank)
//        viewState.showBranch(branch, emptyList())
        interactor.getCurrencyRates(branch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ (bank, exchangeRates) ->
                    viewState.showTitle(bank)
                    viewState.showBranch(bank, branch, exchangeRates)
                }, {
                    it.printStackTrace()
                })
    }

}