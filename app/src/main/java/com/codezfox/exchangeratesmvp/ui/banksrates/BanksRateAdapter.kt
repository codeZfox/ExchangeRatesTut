package com.codezfox.exchangeratesmvp.ui.banksrates

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.entity.Currency
import com.codezfox.exchangeratesmvp.entity.RateBank
import kotlinx.android.synthetic.main.item_bank_rate.view.*


class BanksRateAdapter : RecyclerView.Adapter<BanksRateAdapter.CurrencyRateViewHolder>() {

    private var items: List<RateBank> = listOf()

    inner class CurrencyRateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rate: RateBank) {
            itemView.textViewName.text = rate.bank.name
            itemView.textViewUpdate.text = "Актуально на сегодня в 19:00"
            itemView.textViewBuy.text = Currency.rateForUI(rate.buy)
            itemView.textViewSell.text = Currency.rateForUI(rate.sell)
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
