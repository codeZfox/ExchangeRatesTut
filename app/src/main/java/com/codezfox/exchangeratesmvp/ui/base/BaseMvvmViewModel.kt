package com.codezfox.exchangeratesmvp.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.SerialDisposable

abstract class BaseMvvmViewModel<Router>() : BaseViewModel() {
    lateinit var router: ru.terrakok.cicerone.Router
}

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun Disposable.addDisposable(disposable: SerialDisposable) {
        disposables.add(disposable)
        disposable.set(this)
    }

    fun Disposable.addDisposable(): Disposable {
        disposables.add(this)
        return this
    }

    fun Disposable.toComposite(): Disposable {
        disposables.add(this)
        return this
    }

    fun getDisposable(): CompositeDisposable {
        return disposables
    }

    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    open fun onViewCreated() {

    }

    open fun onResume() {

    }

    open fun onPause() {

    }

}