package com.codezfox.exchangeratesmvp.ui.bankbranch

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Bank
import com.codezfox.exchangeratesmvp.data.models.Branch
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate
import com.codezfox.exchangeratesmvp.extensions.get
import com.codezfox.exchangeratesmvp.ui._base.BaseMvpFragment
import com.codezfox.paginator.screen.updateAdapter
import kotlinx.android.synthetic.main.screen_bank_branch.*
import kotlinx.android.synthetic.main.screen_bank_branch.toolbar
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register


class BankBranchFragment : BaseMvpFragment(), BankBranchView {

    @InjectPresenter
    lateinit var presenter: BankBranchPresenter

    private var adapter = MultiTypeAdapter()

    @ProvidePresenter
    fun providePresenter(): BankBranchPresenter {
        val branch = arguments?.getSerializable("Branch") as Branch
        val bank = arguments?.getSerializable("Bank") as Bank?
        val interactor = BankBranchInteractor(get(), get(), get())
        return BankBranchPresenter(bank, branch, getRouter(), interactor)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_bank_branch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        adapter.register(StringViewBinder({

        }))
        adapter.register(CurrencyRatesHeaderViewBinder())
        adapter.register(ExchangeRateViewBinder({}))
        adapter.register(PhoneViewBinder({

        }))
    }

    override fun showTitle(bank: Bank?) {
        toolbar.title = bank?.name ?: ""
    }

    override fun showBranch(bank: Bank, branch: Branch, exchangeRates: List<CurrencyExchangeRate>) {

        val items = mutableListOf<Any>()
        if (bank.name != branch.name) {
            items.add(branch.name)
        }
        items.add(branch.address)
        items.addAll(branch.getPhonesList().map { PhoneData(it) })
        items.add(CurrencyRatesHeader("Валюта"))
        items.addAll(exchangeRates)
        adapter.updateAdapter(items)
    }

}