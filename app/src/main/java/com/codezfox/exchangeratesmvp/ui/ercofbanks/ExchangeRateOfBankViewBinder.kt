package com.codezfox.exchangeratesmvp.ui.ercofbanks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.BankRate
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import com.codezfox.extensions.isToday
import com.codezfox.extensions.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_bank_rate.*
import java.util.*


class ExchangeRateOfBankViewBinder(private val onClick: (rateBank: BankRate) -> Unit) : ItemViewBinder<BankRate, ExchangeRateOfBankViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(rate: BankRate) {
            textViewName.text = rate.bank.name
            textViewUpdate.text = buildActualTimeString(Date(rate.updateTime * 1000L))
            textViewBuy.text = Currency.rateForUI(rate.sell)
            textViewSell.text = Currency.rateForUI(rate.buy)
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

    override fun onBindViewHolder(holder: ViewHolder, item: BankRate) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

}
