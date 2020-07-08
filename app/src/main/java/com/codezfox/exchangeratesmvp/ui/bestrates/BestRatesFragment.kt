package com.codezfox.exchangeratesmvp.ui.bestrates

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.view.*
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.ui._base.BasePaginatorFragment
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.extensions.*
import com.codezfox.extensions.visibleOrGone
import kotlinx.android.synthetic.main.layout_last_date_data.*
import kotlinx.android.synthetic.main.screen_best_rates.*
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import java.text.SimpleDateFormat
import java.util.*


class BestRatesFragment : BasePaginatorFragment<BestRateCurrency, BestRatesView, BestRatesPresenter>(), BestRatesView {

    @ProvidePresenter
    fun providePresenter(): BestRatesPresenter {
        return BestRatesPresenter(getRouter(), BestRatesInteractor(get(), get(), get()), get())
    }

    @InjectPresenter
    override lateinit var presenter: BestRatesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_best_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun registerTypes(adapter: MultiTypeAdapter) {
        super.registerTypes(adapter)
        adapter.register(BestRatesViewBinder({
            presenter.openCurrency(it)
        }))
    }

    private var simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

    override fun showLastDateUpdated(date: Date?) {
        //todo blink after backPressed
        textViewLastDateData.visibleOrGone(date != null)
        if (date != null) {
            textViewLastDateData.text = "Последнее обновление: " + simpleDateFormat.format(date)
        }
    }

}