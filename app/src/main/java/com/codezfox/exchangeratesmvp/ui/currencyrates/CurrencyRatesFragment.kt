package com.codezfox.exchangeratesmvp.ui.currencyrates

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.entity.CurrencyRate
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
    }

    override fun showRates(items: List<CurrencyRate>) {
        adapter.setItems(items)
    }

}