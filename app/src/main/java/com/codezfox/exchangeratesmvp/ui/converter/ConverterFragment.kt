package com.codezfox.exchangeratesmvp.ui.converter

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.extensions.get
import com.codezfox.exchangeratesmvp.ui._base.BaseMvpFragment
import kotlinx.android.synthetic.main.screen_bank_branch.*
import kotlinx.android.synthetic.main.screen_converter.*
import kotlinx.android.synthetic.main.screen_converter.recyclerView
import kotlinx.android.synthetic.main.screen_converter.toolbar
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register


interface ConverterView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun show(rateCurrency: List<ConverterRate>)

}


data class ConverterRate(
        val bestRateCurrency: BestRateCurrency,
        val summary: String,
        val isSelected: Boolean
)

class ConverterFragment : BaseMvpFragment(), ConverterView {

    @InjectPresenter
    lateinit var presenter: ConverterPresenter

    @ProvidePresenter
    fun providePresenter(): ConverterPresenter {
        val interactor = ConverterInteractor(get(), get())
        return ConverterPresenter(interactor, getRouter())
    }

    private var adapter = MultiTypeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.currency_converter)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        adapter.register(ConverterViewBinder({
            presenter.selectCurrency(it)
            val string = it.summary
            numPad.value = string
        }))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        numPad.editChange = { text ->
            presenter.input(text)
            Log.d("input", "$text")
        }
    }

    override fun show(rateCurrency: List<ConverterRate>) {
//        adapter.updateAdapter(rateCurrency)
        adapter.items = rateCurrency
        adapter.notifyDataSetChanged()
    }
}