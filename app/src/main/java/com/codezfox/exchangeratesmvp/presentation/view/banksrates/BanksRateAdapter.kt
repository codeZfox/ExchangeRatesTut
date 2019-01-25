package com.codezfox.exchangeratesmvp.presentation.view.banksrates

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateBank
import com.codezfox.exchangeratesmvp.extensions.compareWithToday
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_bank_rate.*
import java.util.*


class BanksRateAdapter : RecyclerView.Adapter<BanksRateAdapter.CurrencyRateViewHolder>() {

    private var items: List<RateBank> = listOf()

    inner class CurrencyRateViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(rate: RateBank) {
            textViewName.text = rate.bank.name
            textViewUpdate.text = buildActualTimeString(Date(rate.updateTime * 1000L))
            textViewBuy.text = Currency.rateForUI(rate.sell)
            textViewSell.text = Currency.rateForUI(rate.buy)
        }
    }

    private fun buildActualTimeString(date: Date): String {
        return if (date.compareWithToday() == 0) {
            String.format("Актуально на сегодня в %1\$tH:%1\$tM", date)
        } else {
            String.format("Актуально на %1\$te %1\$tB в %1\$tH:%1\$tM", date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        return CurrencyRateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bank_rate, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(items[position])
        items.isNullOrEmpty()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<RateBank>) {
        if (this.items.isEmpty()) {
            this.items = items
            notifyItemRangeInserted(0, items.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@BanksRateAdapter.items.size
                }

                override fun getNewListSize(): Int {
                    return items.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@BanksRateAdapter.items[oldItemPosition] == items[newItemPosition]
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return items[newItemPosition] == items[oldItemPosition]
                }
            })
            this.items = items
            result.dispatchUpdatesTo(this)
        }
    }


}
