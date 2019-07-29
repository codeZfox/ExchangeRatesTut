package com.codezfox.exchangeratesmvp.ui.ercofbranchesbank

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.extensions.*
import android.support.annotation.ColorInt
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.BranchWithExchangeRate
import com.codezfox.exchangeratesmvp.ui._base.BasePaginatorFragment
import com.codezfox.exchangeratesmvp.ui.ercofbanks.*
import com.codezfox.extensions.*
import kotlinx.android.synthetic.main.layout_currency_rate_header.*
import kotlinx.android.synthetic.main.layout_last_date_data.*
import kotlinx.android.synthetic.main.screen_banks_rates.toolbar
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.text.SimpleDateFormat
import java.util.*


class ExchangeRateCurrencyOfBranchesBankFragment : BasePaginatorFragment<BranchWithExchangeRate, ExchangeRateCurrencyOfBranchesBankView, ExchangeRateCurrencyOfBranchesBankPresenter>(), ExchangeRateCurrencyOfBranchesBankView {

    @ProvidePresenter
    fun providePresenter(): ExchangeRateCurrencyOfBranchesBankPresenter {
        val currency = arguments?.getSerializable("Currency") as Currency
        val bank = arguments?.getSerializable("Bank") as Bank
        val interactor = ExchangeRateCurrencyOfBranchesBankInteractor(get(), get(), get())
        return ExchangeRateCurrencyOfBranchesBankPresenter(currency, bank, interactor, get(), getRouter())
    }

    @InjectPresenter
    override lateinit var presenter: ExchangeRateCurrencyOfBranchesBankPresenter

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
        adapter.register(BranchCurrencyViewBinder({
                                                      presenter.openBankBranch(it.branch)
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

    override fun showTitle(title: String, subTitle: String) {
        toolbar.title = title
        toolbar.subtitle = subTitle
    }

}