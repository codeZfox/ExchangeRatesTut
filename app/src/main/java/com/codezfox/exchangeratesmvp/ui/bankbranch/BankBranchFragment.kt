package com.codezfox.exchangeratesmvp.ui.bankbranch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate
import com.codezfox.exchangeratesmvp.databinding.ScreenBankBranchBinding
import com.codezfox.exchangeratesmvp.ui.DateUpdate
import com.codezfox.exchangeratesmvp.ui.DateUpdateViewBinder
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmFragment
import com.codezfox.exchangeratesmvp.ui.base.adapter.MultiAdapter
import com.codezfox.exchangeratesmvp.ui.base.getString
import com.codezfox.exchangeratesmvp.ui.base.viewModelLazy
import com.codezfox.exchangeratesmvp.ui.bestrates.applyColorsToSwipeToRefresh
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.screen_bank_branch.*
import org.jetbrains.anko.dial
import org.jetbrains.anko.geo
import org.jetbrains.anko.selector
import org.kodein.di.generic.factory2
import ru.terrakok.cicerone.Router


class BankBranchFragment : BaseMvvmFragment<BankBranchViewModel, Router>() {

    val viewModelFactory by factory2<Bank, Branch, BankBranchViewModel>()

    override val viewModel: BankBranchViewModel by viewModelLazy {
        val bank = arguments?.getSerializable("Bank") as Bank
        val branch = arguments?.getSerializable("Branch") as Branch
        viewModelFactory(bank, branch)
    }

    private val adapter by lazy {
        MultiAdapter().also { adapter ->
            adapter.register(DataString::class.java, StringViewBinder {
                when (it.type) {
                    DataString.TYPE.ADDRESS -> context?.let { context -> context.geo(context.getString(it.data)) }
                    DataString.TYPE.PHONE -> context?.let { context -> context.dial(context.getString(it.data)) }
                    DataString.TYPE.SERVICES -> viewModel.openServices()
                    else -> {
                    }
                }
            })
            adapter.register(DateUpdate::class.java, DateUpdateViewBinder())
            adapter.register(CurrencyRatesHeader::class.java, CurrencyRatesHeaderViewBinder())
            adapter.register(CurrencyExchangeRate::class.java, ExchangeRateViewBinder({}))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ScreenBankBranchBinding.inflate(inflater).also { binding ->
            binding.viewModel = viewModel
            binding.adapter = adapter

            viewModel.showServices
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ services ->
                    context?.selector(getString(R.string.services), services.map { it.name })
                }, {
                    it.printStackTrace()
                })
                .addDisposable()

        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { viewModel.onBackPressed() } //todo maybe

        swipeToRefresh.applyColorsToSwipeToRefresh()

    }

}