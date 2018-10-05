package com.codezfox.exchangeratesmvp.ui.currencyrates

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.entity.CurrencyRate
import com.codezfox.exchangeratesmvp.extensions.gone
import com.codezfox.exchangeratesmvp.extensions.isRefreshing
import com.codezfox.exchangeratesmvp.extensions.visible
import com.codezfox.exchangeratesmvp.extensions.visibleOrGone
import com.codezfox.exchangeratesmvp.presentation.currencyrates.CurrencyRatesPresenter
import com.codezfox.exchangeratesmvp.presentation.currencyrates.CurrencyRatesView
import kotlinx.android.synthetic.main.screen_currency_rates.*

class CurrencyRatesFragment : MvpAppCompatFragment(), CurrencyRatesView {

    private val adapter = CurrencyRateAdapter()

    @ProvidePresenter
    fun providePresenter() = CurrencyRatesPresenter()

    @InjectPresenter
    lateinit var presenter: CurrencyRatesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_currency_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadRates()
        }
    }

    override fun showRates(items: List<CurrencyRate>) {
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