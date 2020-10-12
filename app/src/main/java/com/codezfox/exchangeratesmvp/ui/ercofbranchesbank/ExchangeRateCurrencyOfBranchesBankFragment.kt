package com.codezfox.exchangeratesmvp.ui.ercofbranchesbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.BranchWithExchangeRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.databinding.ScreenErcBranchesBankBinding
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmFragment
import com.codezfox.exchangeratesmvp.ui.base.adapter.MultiAdapter
import com.codezfox.exchangeratesmvp.ui.base.viewModelLazy
import com.codezfox.exchangeratesmvp.ui.ercofbanks.RateCurrencySort
import com.codezfox.exchangeratesmvp.ui.ercofbanks.isEqual
import com.codezfox.extensions.onClick
import kotlinx.android.synthetic.main.layout_currency_rate_header.*
import kotlinx.android.synthetic.main.screen_erc_branches_bank.*
import org.kodein.di.generic.factory2
import ru.terrakok.cicerone.Router


class ExchangeRateCurrencyOfBranchesBankFragment : BaseMvvmFragment<ExchangeRateCurrencyOfBranchesBankViewModel, Router>() {

    val viewModelFactory by factory2<Currency, Bank, ExchangeRateCurrencyOfBranchesBankViewModel>()

    override val viewModel: ExchangeRateCurrencyOfBranchesBankViewModel by viewModelLazy {
        val currency = arguments?.getSerializable("Currency") as Currency
        val bank = arguments?.getSerializable("Bank") as Bank
        viewModelFactory(currency, bank)
    }

    private var binding :ScreenErcBranchesBankBinding? = null
    private val adapter by lazy {
        MultiAdapter(onCurrentListChanged = { previousList, currentList ->
            //todo magic scroll
            binding?.recyclerView?.let { recyclerView ->
                val endIndex = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if (!isEqual(previousList.map { it.areItemsTheSame() }, currentList.map { it.areItemsTheSame() }, endIndex)) {
                    recyclerView.smoothScrollToPosition(0)
                }
            }
        }).also { adapter ->
            adapter.register(BranchWithExchangeRate::class.java, BranchCurrencyViewBinder {
                viewModel.openBankBranch(it.branch) //todo maybe
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ScreenErcBranchesBankBinding.inflate(inflater).also { binding ->
            binding.viewModel = viewModel
            binding.adapter = adapter
            this.binding = binding
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { viewModel.onBackPressed() }//todo maybe
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewBuy.onClick {
            viewModel.changeSort(RateCurrencySort.BUY) //todo maybe
        }

        textViewSell.onClick {
            viewModel.changeSort(RateCurrencySort.SELL) //todo maybe
        }

        swipeToRefresh.setColorSchemeColors(ContextCompat.getColor(requireActivity(), R.color.colorPrimary))//todo maybe

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}