package com.codezfox.exchangeratesmvp.ui.banksrates

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
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.RateBank
import com.codezfox.exchangeratesmvp.extensions.gone
import com.codezfox.exchangeratesmvp.extensions.isRefreshing
import com.codezfox.exchangeratesmvp.extensions.visible
import com.codezfox.exchangeratesmvp.extensions.visibleOrGone
import com.codezfox.exchangeratesmvp.presentation.banksrates.BanksRatesPresenter
import com.codezfox.exchangeratesmvp.presentation.banksrates.BanksRatesView
import kotlinx.android.synthetic.main.screen_banks_rates.*

class BanksRatesFragment : MvpAppCompatFragment(), BanksRatesView {

    private val adapter = BanksRateAdapter()

    @ProvidePresenter
    fun providePresenter() = BanksRatesPresenter(arguments?.getSerializable("Currency") as Currency)

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

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadRates()
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