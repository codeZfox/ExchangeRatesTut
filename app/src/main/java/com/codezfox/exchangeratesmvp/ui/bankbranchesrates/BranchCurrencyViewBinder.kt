package com.codezfox.exchangeratesmvp.ui.bankbranchesrates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.BranchCurrency
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.extensions.isToday
import com.codezfox.extensions.onClick
import com.codezfox.extensions.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_bank_rate.*
import me.drakeet.multitype.ItemViewBinder
import java.util.*


class BranchCurrencyViewBinder(private val onClick: (rateBank: BranchCurrency) -> Unit) : ItemViewBinder<BranchCurrency, BranchCurrencyViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(branchCurrency: BranchCurrency) {
            textViewName.text = branchCurrency.branch.name
            val address = s(branchCurrency)
            textViewAddress.text = address
            textViewUpdate.text = buildActualTimeString(Date(branchCurrency.branchRate.updateTime * 1000L))
            textViewBuy.text = Currency.rateForUI(branchCurrency.branchRate.sellRate.toDouble())
            textViewSell.text = Currency.rateForUI(branchCurrency.branchRate.buyRate.toDouble())
        }

        private fun s(branchCurrency: BranchCurrency): String {
            return branchCurrency.branch.address.split(", ", limit = 2).joinToString(", ") { it.replace("\u0020", "\u00A0") }
        }


        private fun buildActualTimeString(date: Date): String {
            return if (date.isToday()) {
                String.format("Актуально на сегодня в %1\$tH:%1\$tM", date)
            } else {
                String.format("Актуально на %1\$te %1\$tB в %1\$tH:%1\$tM", date)
            }
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bank_rate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: BranchCurrency) {
        holder.bindView(item)
        holder.textViewAddress.visible()
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

}
