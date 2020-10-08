package com.codezfox.exchangeratesmvp.ui.bankbranch


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.data.models.CurrencyExchangeRate
import com.codezfox.exchangeratesmvp.data.models.ExchangeRate
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import com.codezfox.extensions.onClick
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_exchange_rate.*


class ExchangeRateViewBinder(private val onClick: (exchangeRate: ExchangeRate) -> Unit) : ItemViewBinder<CurrencyExchangeRate, ExchangeRateViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_exchange_rate, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: CurrencyExchangeRate) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item.branchRate)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(branchCurrency2: CurrencyExchangeRate) {

            val rate = branchCurrency2.branchRate

            val currency = branchCurrency2.currency
            val scale = currency.scale

            textViewName.text = currency.id
            textViewAmount.text = currency.getAmountString()
            textViewBuy.text = Currency.rateForUI(rate.sellRate, scale)
            textViewSell.text = Currency.rateForUI(rate.buyRate, scale)


            Picasso.with(itemView.context)
                    .load(currency.flag)
                    .placeholder(R.drawable.ic_currency_default)
                    .into(imageViewCurrencyFlag)

        }
    }
}
