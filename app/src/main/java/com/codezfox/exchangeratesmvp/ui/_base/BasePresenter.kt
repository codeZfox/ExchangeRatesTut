package com.codezfox.exchangeratesmvp.ui._base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.codezfox.extensions.showMessage
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    protected val disposable = CompositeDisposable()

    protected fun <T> loadData(networkConnectedObservable: Observable<Boolean>,
                               loadData: Observable<T>,
                               subject: Subject<T>,
                               isShowError: Boolean = true): Disposable {
        lateinit var disposable: Disposable

        var request: Disposable? = null

        disposable = networkConnectedObservable
                .doOnNext {
                    request?.dispose()
                    request = loadData
                            .onErrorResumeNext(isShowError)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                subject.onNext(it)
                                disposable.dispose()
                            }, {
                                if (isShowError) {
                                    showError(it)
                                }
                                it.printStackTrace()
                            })

                    request?.let { this.disposable.add(it) }

                }.subscribe({

                }, {
                    if (isShowError) {
                        showError(it)
                    }
                })


        this.disposable.add(disposable)

        return disposable
    }

    private var isShowOnceError = false


    private val showOnceError: (Throwable) -> Unit = { throwable ->
        if (!isShowOnceError) {
            showError(throwable)
            isShowOnceError = true
        }
    }

    private val timer: (Throwable) -> Observable<Long> = {
        Observable.timer(DELAY_REPEAT_LOADING_DATA, TimeUnit.SECONDS)
    }

    private fun <T> Observable<T>.onErrorResumeNext(isShowError: Boolean = true): Observable<T> {
        isShowOnceError = false

        var observable = this

        if (isShowError) {
            observable = observable.observeOn(AndroidSchedulers.mainThread())
                    .doOnError(showOnceError)
        }

        return observable
                .observeOn(Schedulers.io())
                .retryWhen { it.flatMap(timer) }
    }

    open fun showError(throwable: Throwable) {
        viewState.showMessage(throwable.localizedMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    companion object {
        private const val DELAY_REPEAT_LOADING_DATA = 10L
    }

}