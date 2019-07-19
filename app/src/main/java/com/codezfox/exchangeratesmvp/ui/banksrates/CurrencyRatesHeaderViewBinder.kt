package com.codezfox.exchangeratesmvp.ui.banksrates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.extensions.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_string.*
import kotlinx.android.synthetic.main.layout_currency_rate_header.*
import me.drakeet.multitype.ItemViewBinder

class CurrencyRatesHeader(val header: String)

class CurrencyRatesHeaderViewBinder : ItemViewBinder<CurrencyRatesHeader, CurrencyRatesHeaderViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_currency_rate_header, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: CurrencyRatesHeader) {
        holder.textViewHeader.setText(item.header)
    }

}
