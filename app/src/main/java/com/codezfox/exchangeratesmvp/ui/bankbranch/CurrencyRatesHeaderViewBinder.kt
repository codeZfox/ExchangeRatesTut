package com.codezfox.exchangeratesmvp.ui.bankbranch

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_currency_rate_header.*

data class CurrencyRatesHeader(val header: String) : DisplayableItem {
    override fun areItemsTheSame(): String {
        return header
    }
}

class CurrencyRatesHeaderViewBinder : ItemViewBinder<CurrencyRatesHeader, CurrencyRatesHeaderViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_currency_rate_header, parent, false)
//        view.layoutParams = (view.layoutParams as? RecyclerView.LayoutParams)?.apply {
//            this.topMargin = 8.dp
//        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: CurrencyRatesHeader) {
        holder.textViewHeader.setText(item.header)
    }

}
