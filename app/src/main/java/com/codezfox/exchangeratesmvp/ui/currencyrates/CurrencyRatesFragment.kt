package com.codezfox.exchangeratesmvp.ui.currencyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.ui._base.BasePaginatorFragment
import com.codezfox.exchangeratesmvp.data.models.RateCurrency
import com.codezfox.exchangeratesmvp.extensions.*
import com.codezfox.extensions.visibleOrGone
import kotlinx.android.synthetic.main.screen_currency_rates.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.text.SimpleDateFormat
import java.util.*


class CurrencyRatesFragment : BasePaginatorFragment<RateCurrency, CurrencyRatesView, CurrencyRatesPresenter>(), CurrencyRatesView {

    @ProvidePresenter
    fun providePresenter(): CurrencyRatesPresenter {
        return CurrencyRatesPresenter(getRouter(), CurrencyRatesInteractor(get(), get(), get()), get())
    }

    @InjectPresenter
    override lateinit var presenter: CurrencyRatesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_currency_rates, container, false)
    }

    override fun registerTypes(adapter: MultiTypeAdapter) {
        super.registerTypes(adapter)
        adapter.register(CurrencyRatesViewBinder({
            presenter.openCurrency(it)
        }))
    }

    var simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

    override fun showLastDateUpdated(date: Date?) {
        //todo blink after backPressed
        textViewLastDateData.visibleOrGone(date != null)
        if (date != null) {
            textViewLastDateData.text = "Последнее обновление: " + simpleDateFormat.format(date)
        }
    }

}