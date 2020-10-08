package com.codezfox.exchangeratesmvp.ui.ercofbanks

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.databinding.ScreenErcBanksBinding
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmFragment
import com.codezfox.exchangeratesmvp.ui.base.adapter.MultiAdapter
import com.codezfox.exchangeratesmvp.ui.base.viewModelLazyFactory
import com.codezfox.extensions.onClick
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_currency_rate_header.*
import kotlinx.android.synthetic.main.screen_erc_banks.*
import ru.terrakok.cicerone.Router


class ExchangeRateCurrencyOfBanksFragment : BaseMvvmFragment<ExchangeRateCurrencyOfBanksViewModel, Router>() {

    override val viewModel: ExchangeRateCurrencyOfBanksViewModel by viewModelLazyFactory {
        arguments?.getSerializable("Currency") as Currency
    }

    private val adapter by lazy {
        MultiAdapter().also { adapter ->
            adapter.register(BankRate::class.java, ExchangeRateOfBankViewBinder {
                viewModel.openBankExchangeRates(it.bank) //todo maybe
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ScreenErcBanksBinding.inflate(inflater).also { binding ->
            binding.viewModel = viewModel
            binding.adapter = adapter

            viewModel.scrollToTop
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    binding.recyclerView.smoothScrollToPosition(0)
                },{
                    it.printStackTrace()
                })
                .addDisposable()
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { viewModel.onBackPressed() } //todo maybe
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewBuy.onClick {
            viewModel.changeSort(RateCurrencySort.BUY) //todo maybe
        }

        textViewSell.onClick {
            viewModel.changeSort(RateCurrencySort.SELL) //todo maybe
        }

        swipeToRefresh.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))

    }

}