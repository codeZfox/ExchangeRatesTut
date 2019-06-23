package com.codezfox.exchangeratesmvp.presentation.paginator.screen

import com.arellomobile.mvp.MvpPresenter
import com.codezfox.exchangeratesmvp.data.repositories.SystemRepository
import com.codezfox.exchangeratesmvp.extensions.showMessage
import com.codezfox.exchangeratesmvp.presentation.paginator.screen.MvpPaginatorPresenter.DefaultViewController
import com.minsk2019.android.minsk2019.paginator.Paginator
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class MvpPaginatorPresenter<T, V : PaginatorView<T>> : MvpPresenter<V>(), IMvpPaginatorPresenter<T, V> {

    abstract fun requestFactory(page: Int): Single<List<T>>

    private var isFirstAttach = true

    override fun attachView(view: V) {
        super.attachView(view)
        if (!isFirstAttach) {
            pagination.show()
        }
        isFirstAttach = false
    }

    protected val disposable = CompositeDisposable()


    fun subscribeToNetworkConnected(systemRepository: SystemRepository) {
        disposable.add(systemRepository.getNetworkConnected(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    pagination.networkAvailable()
                }, {
                    it.printStackTrace()
                }))
    }

    open inner class DefaultViewController : Paginator.ViewController<T> {

        override fun showEmptyError(show: Boolean, error: Throwable?) {
            if (show) {
                viewState.showEmptyError(error)
            } else {
                viewState.hideEmptyError()
            }
        }

        override fun showErrorMessage(show: Boolean, error: Throwable?) {
            if (show) {
                viewState.showError(error ?: Exception("null"))
            } else {
                viewState.hideError()

            }
        }

        override fun showEmptyView(show: Boolean) {
            viewState.showEmptyView(show)
        }

        override fun showData(show: Boolean, data: List<T>, toTop: Boolean) {
            viewState.updateAdapter(data, toTop)
        }

        override fun showRefreshProgress(show: Boolean) {
            if (show) {
                viewState.showLoading()
            } else {
                viewState.hideLoading()
            }
        }

        override fun showPageProgress(show: Boolean) {
            viewState.showPageProgress(show)
        }

        override fun showMessage(text: String) {
            viewState.showMessage(text)
        }
    }

    protected open fun getViewController(): Paginator.ViewController<T> = DefaultViewController()

    protected val pagination = Paginator(::requestFactory, getViewController())

    protected open fun getOffset(page: Int) = (page - 1) * 20

    override fun refresh() = pagination.refresh()

    override fun loadMore() {
        if (!pagination.isError()) {
            pagination.loadNewPage()
        }
    }

    override fun retryLoadMore() {
        pagination.loadNewPage()
    }

    override fun onDestroy() {
        super.onDestroy()
        pagination.release()
        disposable.dispose()
    }

}