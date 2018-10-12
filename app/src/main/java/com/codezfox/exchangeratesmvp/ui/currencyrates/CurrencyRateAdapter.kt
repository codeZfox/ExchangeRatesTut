package com.codezfox.exchangeratesmvp.ui.currencyrates

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.entity.Rate
import com.codezfox.exchangeratesmvp.entity.RateCurrency
import com.codezfox.exchangeratesmvp.extensions.gone
import com.codezfox.exchangeratesmvp.extensions.resources
import com.codezfox.exchangeratesmvp.extensions.visible
import com.codezfox.exchangeratesmvp.extensions.visibleOrInvisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import java.util.*


class CurrencyRateAdapter : RecyclerView.Adapter<CurrencyRateAdapter.CurrencyRateViewHolder>() {

    private var items: List<RateCurrency> = listOf()
    private var mapFormats = hashMapOf<Int, String>()

    inner class CurrencyRateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var scale: Int = 4

        private fun rateForUI(value: Double): String {
            return String.format(Locale.UK, mapFormats[scale]!!, value)
        }

        private fun rateDiffForUI(value: Double): String {
            val string = String.format(Locale.UK, mapFormats[scale]!!, value)
            if (value > 0) {
                return "+$string"
            }
            return string
        }

        fun bind(rateCurrency: RateCurrency) {

            val rate = rateCurrency.rate

            scale = rateCurrency.currency?.scale ?: 4

            if (!mapFormats.containsKey(scale)) {
                mapFormats[scale] = "%.${scale}f"
            }

            itemView.textViewName.text = rate.currencyCode
            itemView.textViewAmount.text = rateCurrency.getAmountString()
            itemView.textViewBuy.text = rateForUI(rate.sell)
            itemView.textViewSell.text = rateForUI(rate.buy)
            itemView.textViewNb.text = rateForUI(rate.nb)

            if (rate.bcse_date == null || rate.bcse_date != rate.nb_date && rate.nb == rate.bcse_rate) {

                itemView.textViewBCSERoot.gone()

                itemView.textViewNbDiff.visibleOrInvisible(rate.nb_diff != 0.0)
                itemView.textViewNbDiff.setTextColor(resources.getColor(if (rate.nb_diff >= 0) R.color.colorGreen else R.color.colorRed))
                itemView.textViewNbDiff.text = rateDiffForUI(rate.nb_diff)

            } else {

                itemView.textViewNbDiff.gone()

                itemView.textViewBCSERoot.visible()

                itemView.textViewBCSEDate.text = String.format(Locale("ru"), itemView.context.getString(R.string.BCSE_date), rate.bcse_date)
                itemView.textViewBCSERate.text = rateDiffForUI(rate.bcse_rate)

                itemView.textViewBCSEDiff.visibleOrInvisible(rate.bcse_diff != 0.0)
                itemView.textViewBCSEDiff.text = rateDiffForUI(rate.bcse_diff)
                itemView.textViewBCSEDiff.setTextColor(resources.getColor(if (rate.bcse_diff >= 0) R.color.colorGreen else R.color.colorRed))

            }

            Picasso.with(itemView.context)
                    .load(rateCurrency.currency?.flag)
                    .placeholder(R.drawable.ic_currency_default)
                    .into(itemView.imageViewCurrencyFlag)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        return CurrencyRateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_currency_rate, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(items[position])
        items.isNullOrEmpty()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<RateCurrency>) {
        if (this.items.isEmpty()) {
            this.items = items
            notifyItemRangeInserted(0, items.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@CurrencyRateAdapter.items.size
                }

                override fun getNewListSize(): Int {
                    return items.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@CurrencyRateAdapter.items[oldItemPosition] == items[newItemPosition]
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
