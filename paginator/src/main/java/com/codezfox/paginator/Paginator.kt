package com.minsk2019.android.minsk2019.paginator

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Paginator<T>(
        private val requestFactory: (Int) -> Single<List<T>>,
        private val viewController: ViewController<T>
) {

    interface ViewController<T> {
        fun showEmptyError(show: Boolean, error: Throwable? = null)
        fun showEmptyView(show: Boolean)
        fun showData(show: Boolean, data: List<T> = emptyList(), toTop: Boolean = false)
        fun showErrorMessage(show: Boolean, error: Throwable? = null)
        fun showRefreshProgress(show: Boolean)
        fun showPageProgress(show: Boolean)
        fun showMessage(text: String)
    }

    private val FIRST_PAGE = 1

    private var currentState: State<T> = EMPTY()
        set(value) {
            field = value
            field.init()
            println("ME STATE: ${value.javaClass.simpleName}")
        }
    private var currentPage = 0
    private val currentData = mutableListOf<T>()
    private var disposable: Disposable? = null

    fun isError() = currentState is DATA_ERROR || currentState is EMPTY_ERROR

    fun restart() {
        currentState.restart()
    }

    private fun setDataState(state: State<T>, list: List<T>) {
        if (list.isNotEmpty()) {
            if (currentState !is EMPTY_ERROR) {
                currentData.clear()
                currentData.addAll(list)

                currentState = state
                currentState.init()
            }
        }

    }

    fun setData(list: List<T>, toTop: Boolean) {
        setDataState(DATA(currentPage, toTop), list)
    }

    fun setAllData(list: List<T>, toTop: Boolean) {
        setDataState(ALL_DATA(toTop), list)
    }

    fun refresh() {
        currentState.refresh()
    }

    fun networkAvailable() {
        currentState.networkAvailable()
    }

    fun loadNewPage() {
        currentState.loadNewPage()
    }

    fun release() {
        currentState.release()
    }

    private fun loadPage(page: Int) {
        disposable?.dispose()
        disposable = requestFactory
                .invoke(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    currentState.newData(it)
                }, {
                    currentState.fail(it)
                    it.printStackTrace()
                })
    }

    fun show() {
        currentState.show()
    }

    private interface State<T> {
        fun init() {}
        fun show() {}//todo maybe q
        fun restart() {}
        fun refresh() {}
        fun networkAvailable() {}
        fun loadNewPage() {}
        fun release() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
    }

    private inner class EMPTY : State<T> {

        override fun refresh() {
            currentState = EMPTY_PROGRESS()
        }

        override fun restart() {
            currentState = EMPTY_PROGRESS()
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class EMPTY_DATA_PROGRESS : EMPTY_PROGRESS() {

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(true)
            viewController.showData(false)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(true)
            viewController.showPageProgress(false)
        }

    }

    private open inner class EMPTY_PROGRESS : State<T> {

        override fun init() {
            show()
            loadPage(FIRST_PAGE)
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(false)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(true)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = EMPTY_PROGRESS()
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentData.clear()
                currentData.addAll(data)
                currentState = DATA(FIRST_PAGE, true)
            } else {
                currentState = EMPTY_DATA()
            }
        }

        override fun fail(error: Throwable) {
            currentState = EMPTY_ERROR(error)
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class EMPTY_PROGRESS_ERROR(private val error: Throwable) : State<T> {

        override fun init() {
            show()
            loadPage(FIRST_PAGE)
        }

        override fun show() {
            viewController.showEmptyError(true, error)
            viewController.showEmptyView(false)
            viewController.showData(false)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(true)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = EMPTY_PROGRESS()
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentData.clear()
                currentData.addAll(data)
                currentState = DATA(FIRST_PAGE, true)
            } else {
                currentState = EMPTY_DATA()
            }
        }

        override fun fail(error: Throwable) {
            currentState = EMPTY_ERROR(error)
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class EMPTY_ERROR(private val error: Throwable) : State<T> {

        override fun init() {
            show()
        }

        override fun show() {
            viewController.showEmptyError(true, error)
            viewController.showEmptyView(false)
            viewController.showData(false)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(false)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = EMPTY_PROGRESS_ERROR(error)
        }

        override fun refresh() {
            currentState = EMPTY_PROGRESS_ERROR(error)
        }

        override fun networkAvailable() {
            refresh()
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class EMPTY_DATA : State<T> {

        override fun init() {
            currentData.clear()
            show()
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(true)
            viewController.showData(false)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(false)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = EMPTY_DATA_PROGRESS()
        }

        override fun refresh() {
            currentState = EMPTY_DATA_PROGRESS()
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class DATA(private val newPage: Int, private val toTop: Boolean = false) : State<T> {

        override fun init() {
            currentPage = newPage
            show()
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(true, currentData, toTop)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(false)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = DATA_RESTART_PROGRESS()
        }

        override fun refresh() {
            currentState = REFRESH()
        }

        override fun loadNewPage() {
            currentState = DATA_PROGRESS()
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class DATA_ERROR(private val error: Throwable) : State<T> {

        override fun init() {
            show()
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(true, currentData)
            viewController.showErrorMessage(true, error)
            viewController.showRefreshProgress(false)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = EMPTY_PROGRESS_ERROR(error)
        }

        override fun refresh() {
            currentState = REFRESH()
        }

        override fun networkAvailable() {
            loadNewPage()
        }

        override fun loadNewPage() {
            currentState = DATA_PROGRESS()
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class REFRESH : State<T> {

        override fun init() {
            show()
            loadPage(FIRST_PAGE)
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(true, currentData)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(true)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = DATA_RESTART_PROGRESS()
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentData.clear()
                currentData.addAll(data)
                currentState = DATA(FIRST_PAGE, true)
            } else {
                currentState = EMPTY_DATA()
            }
        }

        override fun fail(error: Throwable) {
            viewController.showMessage(error.localizedMessage)
            currentState = DATA_ERROR(error)
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class DATA_PROGRESS : State<T> {

        override fun init() {
            show()
            loadPage(currentPage + 1)
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(true, currentData)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(false)
            viewController.showPageProgress(true)
        }

        override fun restart() {
            currentState = DATA_RESTART_PROGRESS()
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentData.addAll(data)
                currentState = DATA(currentPage + 1)
            } else {
                currentState = ALL_DATA()
            }
        }

        override fun refresh() {
            currentState = REFRESH()
        }

        override fun fail(error: Throwable) {
            currentState = DATA_ERROR(error)
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class DATA_RESTART_PROGRESS : State<T> {

        override fun init() {
            show()
            loadPage(FIRST_PAGE)
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(true, currentData)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(true)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = DATA_RESTART_PROGRESS()
        }

        override fun newData(data: List<T>) {
            currentData.clear()
            if (data.isNotEmpty()) {
                currentData.addAll(data)
                currentState = DATA(currentPage + 1, true)
            } else {
                currentState = EMPTY_DATA()
            }
        }

        override fun refresh() {
            currentState = REFRESH()
        }

        override fun fail(error: Throwable) {
            currentState = EMPTY_ERROR(error)
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class ALL_DATA(private val toTop: Boolean = false) : State<T> {

        override fun init() {
            show()
        }

        override fun show() {
            viewController.showEmptyError(false)
            viewController.showEmptyView(false)
            viewController.showData(true, currentData, toTop)
            viewController.showErrorMessage(false)
            viewController.showRefreshProgress(false)
            viewController.showPageProgress(false)
        }

        override fun restart() {
            currentState = DATA_RESTART_PROGRESS()
        }

        override fun refresh() {
            currentState = REFRESH()
        }

        override fun release() {
            currentState = RELEASED()
            disposable?.dispose()
        }
    }

    private inner class RELEASED : State<T>

}
