package com.codezfox.paginator.screen

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.extensions.AddToEndSingleByTagStateStrategy

interface PaginatorView<T> : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun updateAdapter(items: List<T>, toTop: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "Loading")
    fun showLoading()

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "Loading")
    fun hideLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyView(show: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "emptyError")
    fun showEmptyError(error: Throwable?)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "emptyError")
    fun hideEmptyError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPageProgress(show: Boolean)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "Error")
    fun showError(throwable: Throwable)

    @StateStrategyType(AddToEndSingleByTagStateStrategy::class, tag = "Error")
    fun hideError()
}