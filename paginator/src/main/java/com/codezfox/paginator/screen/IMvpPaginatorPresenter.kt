package com.codezfox.paginator.screen


interface IMvpPaginatorPresenter<T, V : PaginatorView<T>> {

    fun refresh()

    fun loadMore()

    fun retryLoadMore()

}