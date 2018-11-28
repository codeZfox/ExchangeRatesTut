package com.codezfox.exchangeratesmvp.presentation.view.currencyrates

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.domain.models.Currency.Companion.rateDiffForUI
import com.codezfox.exchangeratesmvp.domain.models.Currency.Companion.rateForUI
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.extensions.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import java.util.*


class CurrencyRateAdapter : RecyclerView.Adapter<CurrencyRateAdapter.CurrencyRateViewHolder>() {

    private var items: List<RateCurrency> = listOf()
    private var mapFormats = hashMapOf<Int, String>()

    var onClick: ((rateCurrency: RateCurrency) -> Unit)? = null

    inner class CurrencyRateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rateCurrency: RateCurrency) {

            itemView.onClick {
                onClick?.invoke(rateCurrency)
            }

            val rate = rateCurrency.rate

            val scale = rateCurrency.currency.scale


            itemView.textViewName.text = rate.currencyCode
            itemView.textViewAmount.text = rateCurrency.getAmountString()
            itemView.textViewBuy.text = rateForUI(rate.sell, scale)
            itemView.textViewSell.text = rateForUI(rate.buy, scale)
            itemView.textViewNb.text = rateForUI(rate.nb, scale)

            if (rate.bcse_date == null || rate.bcse_date != rate.nb_date && rate.nb == rate.bcse_rate) {

                itemView.textViewBCSERoot.gone()

                itemView.textViewNbDiff.visibleOrInvisible(rate.nb_diff != 0.0)
                itemView.textViewNbDiff.setTextColor(resources.getColor(if (rate.nb_diff >= 0) R.color.colorGreen else R.color.colorRed))
                itemView.textViewNbDiff.text = rateDiffForUI(rate.nb_diff, scale)

            } else {

                itemView.textViewNbDiff.gone()

                itemView.textViewBCSERoot.visible()

                itemView.textViewBCSEDate.text = String.format(Locale("ru"), itemView.context.getString(R.string.BCSE_date), rate.bcse_date)
                itemView.textViewBCSERate.text = rateForUI(rate.bcse_rate, scale)

                itemView.textViewBCSEDiff.visibleOrInvisible(rate.bcse_diff != 0.0)
                itemView.textViewBCSEDiff.text = rateDiffForUI(rate.bcse_diff, scale)
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
