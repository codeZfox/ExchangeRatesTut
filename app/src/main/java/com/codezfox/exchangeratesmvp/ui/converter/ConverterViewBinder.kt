package com.codezfox.exchangeratesmvp.ui.converter


import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.extensions.*
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_converter.*
import me.drakeet.multitype.ItemViewBinder


class ConverterViewBinder(private val onClick: (exchangeRate: ConverterRate) -> Unit) : ItemViewBinder<ConverterRate, ConverterViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_converter, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ConverterRate) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(rate: ConverterRate) {


            val currency = rate.bestRateCurrency.currency

            textViewName.text = currency.id
//            textViewSell.text = Currency.rateForUI(rate.summary, scale)
            textViewSell.text = rate.summary
            textViewSell.setTypeface(null, if (rate.isSelected) Typeface.BOLD else Typeface.NORMAL)
            textViewSell.setTextSize(if (rate.isSelected) 22f else 18f)


            Picasso.with(itemView.context)
                    .load(currency.flag)
                    .placeholder(R.drawable.ic_currency_default)
                    .into(imageViewCurrencyFlag)

        }
    }
}
