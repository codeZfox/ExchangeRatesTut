package com.codezfox.exchangeratesmvp.presentation.view.banksrates

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.extensions.*
import com.codezfox.exchangeratesmvp.presentation.presenter.banksrates.BanksRatesPresenter
import com.codezfox.exchangeratesmvp.presentation.presenter.banksrates.BanksRatesView
import kotlinx.android.synthetic.main.screen_banks_rates.*
import android.support.annotation.ColorInt
import com.codezfox.exchangeratesmvp.domain.banksrates.BanksRatesInteractor
import com.codezfox.exchangeratesmvp.domain.banksrates.RateCurrencySort
import com.codezfox.exchangeratesmvp.presentation.view.BaseMvpFragment
import com.codezfox.extensions.*


class BanksRatesFragment : BaseMvpFragment(), BanksRatesView {

    private val adapter = BanksRateAdapter()

    @ProvidePresenter
    fun providePresenter(): BanksRatesPresenter {
        val currency = arguments?.getSerializable("Currency") as Currency
        val interactor = BanksRatesInteractor(get(), get())
        return BanksRatesPresenter(currency, interactor, getRouter())
    }

    @InjectPresenter
    lateinit var presenter: BanksRatesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_banks_rates, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewBuy.onClick {
            presenter.changeSort(RateCurrencySort.BUY)
        }

        textViewSell.onClick {
            presenter.changeSort(RateCurrencySort.SELL)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadRates()
        }
    }

    private val d: Int by lazy {
        //        val attrs = intArrayOf(android.R.attr.textColorSecondary)
//        val a = activity!!.obtainStyledAttributes(R.style.AppTheme, attrs)
//         a.getColor(0, Color.RED)
//        a.recycle()
        getDefaultThemeColor(android.R.attr.textColorSecondary)
    }

    @ColorInt
    fun getDefaultThemeColor(attribute: Int): Int {
        val themeArray = context!!.getTheme().obtainStyledAttributes(intArrayOf(attribute))
        try {
            val index = 0
            val defaultColourValue = 0
            return themeArray.getColor(index, defaultColourValue)
        } finally {
            themeArray.recycle()
        }
    }

    override fun showSortType(sort: RateCurrencySort) {
        when (sort) {
            RateCurrencySort.BUY -> {
                textViewBuy.setTextColor(resources.getColor(R.color.colorRed))
                textViewSell.setTextColor(d)
            }
            RateCurrencySort.SELL -> {
                textViewBuy.setTextColor(d)
                textViewSell.setTextColor(resources.getColor(R.color.colorRed))
            }
        }
    }

    override fun showCurrencyInfo(currency: Currency) {
        toolbar.title = currency.name
    }

    override fun showRates(items: List<RateBank>) {
        recyclerView.visible()
        adapter.setItems(items)
    }

    override fun showEmptyText(text: String) {
        textViewEmptyList.text = text
        textViewEmptyList.visible()
    }

    override fun hideEmptyText() {
        textViewEmptyList.gone()
    }

    override fun showShimmerEffect(show: Boolean) {
        shimmerView.visibleOrGone(show)
    }

    override fun showProgress(show: Boolean) {
        swipeRefreshLayout.isRefreshing(show)
    }

}