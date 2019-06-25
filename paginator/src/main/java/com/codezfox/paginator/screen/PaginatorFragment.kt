package com.codezfox.paginator.screen

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.arellomobile.mvp.MvpAppCompatFragment
import com.codezfox.extensions.*
import com.codezfox.paginator.R
import kotlinx.android.synthetic.main.fragment_list_paginator.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register


abstract class PaginatorFragment<T, V : PaginatorView<T>, P : IMvpPaginatorPresenter<T, V>> : MvpAppCompatFragment(), PaginatorView<T> {

    abstract var presenter: P

    lateinit var swipeToRefresh: SwipeRefreshLayout

    lateinit var recyclerView: RecyclerView

    private var adapter = MultiTypeAdapter()

    private var errorViewHolder: ErrorView? = null
    private var emptyViewHolder: EmptyView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_paginator, container, false)
    }

    open fun getErrorView(): ErrorView? {
        return ErrorViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_error, rootPaginator, false) as ViewGroup)
    }

    open fun getEmptyView(): EmptyView? {
        return EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_emty, rootPaginator, false) as ViewGroup)
    }

    open fun getItemDecorations(): List<RecyclerView.ItemDecoration> {
        return listOf()
    }

    protected open fun registerTypes(adapter: MultiTypeAdapter) {
        adapter.register(RetryViewBinder())
        adapter.register(ProgressViewBinder())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        errorViewHolder = getErrorView()?.also {
            it.getView().let { view ->
                view.id = R.id.errorLayout
                view.gone()
                rootPaginator.addView(view, 0)
            }
        }

        emptyViewHolder = getEmptyView()?.also {
            it.getView().let { view ->
                view.id = R.id.emptyLayout
                view.gone()
                rootPaginator.addView(view, 0)
            }
        }

        swipeToRefresh = view.findViewById(R.id.flp_swipe_to_refresh)
        recyclerView = view.findViewById(R.id.flp_list)

        swipeToRefresh.setColorSchemeResources(themeAttributeToColor(R.attr.colorPrimary)!!)
        swipeToRefresh.setOnRefreshListener { presenter.refresh() }

        registerTypes(adapter)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        getItemDecorations().forEach {
            recyclerView.addItemDecoration(it)
        }
        recyclerView.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val visibleItemCount = recyclerView.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                        if (totalItemCount - visibleItemCount <= firstVisibleItem + visibleItemCount) {
                            if (layoutManager.findLastCompletelyVisibleItemPosition() >= totalItemCount - 1) {
                                presenter.loadMore()
                            }
                        }
                    }
                })
        recyclerView.adapter = adapter

//        val heightPlaceHolder = arguments?.getInt(ARG_HEIGHT_PLACE_HOLDER)
//
//        if (heightPlaceHolder != null && heightPlaceHolder != 0) {
//            emptyViewHolder?.setHeight(heightPlaceHolder)
//            errorViewHolder?.setHeight(heightPlaceHolder)
//        }

        val minHeight = 100.dp
//        if (arguments?.getBoolean(ARG_IS_DYNAMIC_HEIGHT_PLACE_HOLDER) == true) {
        errorViewHolder?.getView()?.let { errorLayout ->
            errorLayout.viewTreeObserver?.addOnGlobalLayoutListener(
                    object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {

                            errorLayout.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                            val outLocation = IntArray(2)
                            errorLayout.getLocationOnScreen(outLocation)

                            val h = context?.getDisplayHeight()?.minus(outLocation[1])

                            if (h != null && h > minHeight) {
                                emptyViewHolder?.setHeight(h)
                                errorViewHolder?.setHeight(h)
                            }

                        }
                    }
            )
        }
//        }

    }

    override fun updateAdapter(items: List<T>, toTop: Boolean) {
        if (toTop) {
            recyclerView.scrollToPosition(0)
        }
        adapter.updateAdapter(items)

    }

    override fun showLoading() {
        swipeToRefresh.isRefreshing(true)
    }

    override fun hideLoading() {
        swipeToRefresh.isRefreshing(false)
    }

    override fun showError(throwable: Throwable) {
        adapter.showRetry(true, throwable.localizedMessage) {
            presenter.retryLoadMore()
        }
    }

    override fun hideError() {
        adapter.showRetry(false)
    }

    override fun showEmptyView(show: Boolean) {
        if (show) {
            emptyViewHolder?.show()
        } else {
            emptyViewHolder?.hide()
        }
    }

    private val refresh = { presenter.refresh() }

    override fun showEmptyError(error: Throwable?) {
        errorViewHolder?.showError(msg = error?.message) {
            presenter.refresh()
        }
    }

    override fun hideEmptyError() {
        errorViewHolder?.hide()
    }

    override fun showPageProgress(show: Boolean) {
        adapter.showProgress(show)
        if (show) {
            recyclerView.scrollToPosition(adapter.items.lastIndex)
        }
    }

}