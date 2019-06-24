package com.codezfox.paginator.screen

import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.arellomobile.mvp.MvpAppCompatFragment
import com.codezfox.extensions.dp
import com.codezfox.extensions.getDisplayHeight
import com.codezfox.extensions.isRefreshing
import com.codezfox.extensions.themeAttributeToColor
import com.codezfox.paginator.R
import kotlinx.android.synthetic.main.fragment_list_paginator.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register


abstract class PaginatorFragment<T, V : PaginatorView<T>, P : IMvpPaginatorPresenter<T, V>> : MvpAppCompatFragment(), PaginatorView<T> {

    abstract var presenter: P

    lateinit var swipeToRefresh: SwipeRefreshLayout

    lateinit var recyclerView: RecyclerView

    lateinit var errorLayout: View

    private var adapter = MultiTypeAdapter()

    private var errorViewHolder: ErrorViewHolder? = null
    private var emptyViewHolder: EmptyViewHolder? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_paginator, container, false)
    }

    open fun getItemDecorations(): List<RecyclerView.ItemDecoration> {
        return listOf()
    }

    protected open fun onInitPlaceHolderView(errorViewHolder: ErrorViewHolder, emptyViewHolder: EmptyViewHolder) {

    }

    protected open fun registerTypes(adapter: MultiTypeAdapter) {
        adapter.register(RetryViewBinder())
        adapter.register(ProgressViewBinder())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        swipeToRefresh = view.findViewById(R.id.flp_swipe_to_refresh)
        recyclerView = view.findViewById(R.id.flp_list)
        errorLayout = view.findViewById(R.id.errorLayout)

        swipeToRefresh.setColorSchemeResources(themeAttributeToColor(R.attr.colorPrimary)!!)
        swipeToRefresh.setOnRefreshListener { presenter.refresh() }

        registerTypes(adapter)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        getItemDecorations().forEach {
            recyclerView.addItemDecoration(it)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        errorViewHolder = errorLayout?.let { ErrorViewHolder(it as ViewGroup) }
        emptyViewHolder = EmptyViewHolder(emptyLayout as ViewGroup)

//        val heightPlaceHolder = arguments?.getInt(ARG_HEIGHT_PLACE_HOLDER)
//
//        if (heightPlaceHolder != null && heightPlaceHolder != 0) {
//            emptyViewHolder?.setHeight(heightPlaceHolder)
//            errorViewHolder?.setHeight(heightPlaceHolder)
//        }

        val minHeight = 100.dp
//        if (arguments?.getBoolean(ARG_IS_DYNAMIC_HEIGHT_PLACE_HOLDER) == true) {
        errorLayout?.viewTreeObserver?.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        errorLayout?.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                        val outLocation = IntArray(2)
                        errorLayout?.getLocationOnScreen(outLocation)

                        val h = context?.getDisplayHeight()?.minus(outLocation[1])

                        if (h != null && h > minHeight) {
                            emptyViewHolder?.setHeight(h)
                            errorViewHolder?.setHeight(h)
                        }

                    }
                }
        )
//        }

        onInitPlaceHolderView(errorViewHolder!!, emptyViewHolder!!)

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
            emptyViewHolder?.showEmptyData()
        } else {
            emptyViewHolder?.hide()
        }
    }

    private val refresh = { presenter.refresh() }

    override fun showEmptyError(error: Throwable?) {
        errorViewHolder?.showEmptyError(msg = error?.message) {
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