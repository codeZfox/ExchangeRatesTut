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
import com.codezfox.exchangeratesmvp.data.models.Service
import com.codezfox.exchangeratesmvp.extensions.get
import com.codezfox.exchangeratesmvp.ui.DateUpdate
import com.codezfox.exchangeratesmvp.ui.DateUpdateViewBinder
import com.codezfox.exchangeratesmvp.ui._base.BaseMvpFragment
import com.codezfox.extensions.isRefreshing
import com.codezfox.extensions.themeAttributeToColor
import com.codezfox.paginator.screen.updateAdapter
import kotlinx.android.synthetic.main.screen_bank_branch.*
import kotlinx.android.synthetic.main.screen_bank_branch.toolbar
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.register
import org.jetbrains.anko.*
import java.util.*


class BankBranchFragment : BaseMvpFragment(), BankBranchView {

    @InjectPresenter
    lateinit var presenter: BankBranchPresenter

    private var adapter = MultiTypeAdapter()

    @ProvidePresenter
    fun providePresenter(): BankBranchPresenter {
        val branch = arguments?.getSerializable("Branch") as Branch
        val bank = arguments?.getSerializable("Bank") as Bank
        val interactor = BankBranchInteractor(get(), get(), get())
        return BankBranchPresenter(bank, branch, getRouter(), get(), interactor)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_bank_branch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }


        swipeRefreshLayout.setColorSchemeResources(themeAttributeToColor(com.codezfox.paginator.R.attr.colorPrimary)!!)
        swipeRefreshLayout.setOnRefreshListener {
            presenter.refresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        adapter.register(StringViewBinder({
            when (it.type) {
                DataString.TYPE.ADDRESS -> context?.geo(it.data)
                DataString.TYPE.PHONE -> context?.dial(it.data)
                DataString.TYPE.SERVICES -> presenter.openServices()
                else -> {
                }
            }
        }))
        adapter.register(DateUpdateViewBinder())
        adapter.register(CurrencyRatesHeaderViewBinder())
        adapter.register(ExchangeRateViewBinder({}))
    }

    override fun showTitle(bank: Bank?) {
        toolbar.title = bank?.name ?: ""
    }

    override fun showBranch(bank: Bank, branch: Branch, exchangeRates: List<CurrencyExchangeRate>, date: Date?) {

        val items = mutableListOf<Any>()

        if (bank.name != branch.name) {
            items.add(DataString(branch.name, DataString.TYPE.STRING))
        }

        items.add(DataString(branch.address, DataString.TYPE.ADDRESS))
        items.addAll(branch.getPhonesList().map { DataString(it, DataString.TYPE.PHONE) })

        if (!branch.services.isNullOrEmpty()) {
            items.add(DataString(getString(R.string.services), DataString.TYPE.SERVICES))
        }

        if (date != null) {
            items.add(DateUpdate(date))
        }

        if (exchangeRates.isNotEmpty()) {
            items.add(CurrencyRatesHeader("Валюта"))
            items.addAll(exchangeRates)
        }

        adapter.updateAdapter(items)
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing(true)
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing(false)
    }

    override fun openServices(services: List<Service>) {
        context?.selector(getString(R.string.services), services.map { it.name })
    }
}