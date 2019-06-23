package com.codezfox.exchangeratesmvp.presentation.paginator.screen

import com.arellomobile.mvp.MvpView

interface PaginatorView<T> : MvpView {

    fun updateAdapter(items: List<T>, toTop: Boolean)

    fun showLoading()

    fun hideLoading()

    fun showEmptyView(show: Boolean)

    fun showEmptyError(error: Throwable?)

    fun hideEmptyError()

    fun showPageProgress(show: Boolean)

    fun showError(throwable: Throwable)

    fun hideError()
}