package com.codezfox.exchangeratesmvp.ui.ercofbanks

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.exchangeratesmvp.extensions.*
import android.support.annotation.ColorInt
import com.codezfox.exchangeratesmvp.ui._base.BasePaginatorFragment
import com.codezfox.extensions.*
import kotlinx.android.synthetic.main.layout_currency_rate_header.*
import kotlinx.android.synthetic.main.layout_last_date_data.*
import kotlinx.android.synthetic.main.screen_banks_rates.toolbar
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.text.SimpleDateFormat
import java.util.*


class ExchangeRateCurrencyOfBanksFragment : BasePaginatorFragment<BankRate, ExchangeRateCurrencyOfBanksView, ExchangeRateCurrencyOfBanksPresenter>(), ExchangeRateCurrencyOfBanksView {

    @ProvidePresenter
    fun providePresenter(): ExchangeRateCurrencyOfBanksPresenter {
        val currency = arguments?.getSerializable("Currency") as Currency
        val interactor = ExchangeRateCurrencyOfBanksInteractor(get(), get(), get())
        return ExchangeRateCurrencyOfBanksPresenter(currency, interactor, get(), getRouter())
    }

    @InjectPresenter
    override lateinit var presenter: ExchangeRateCurrencyOfBanksPresenter

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

        swipeToRefresh.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))

    }

    override fun registerTypes(adapter: MultiTypeAdapter) {
        super.registerTypes(adapter)
        adapter.register(ExchangeRateOfBankViewBinder({
            presenter.openBankExchangeRates(it.bank)
        }))
    }


    var simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

    override fun showLastDateUpdated(date: Date?) {
        textViewLastDateData.visibleOrGone(date != null)
        if (date != null) {
            textViewLastDateData.text = "Последнее обновление: " + simpleDateFormat.format(date)
        }
    }

    private val d: Int by lazy {
        context!!.getDefaultThemeColor(android.R.attr.textColorSecondary)
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

}