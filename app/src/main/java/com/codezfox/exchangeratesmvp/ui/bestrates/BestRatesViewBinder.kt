package com.codezfox.exchangeratesmvp.ui.bestrates


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.extensions.getDefaultThemeColor
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import com.codezfox.extensions.*
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_rate.*
import java.util.*


class BestRatesViewBinder(private val onClick: (rateCurrency: BestRateCurrency) -> Unit) : ItemViewBinder<BestRateCurrency, BestRatesViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_currency_rate, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: BestRateCurrency) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(rateCurrency: BestRateCurrency) {

//            containerView.onClick {
//                onClick.invoke(rateCurrency)
//            }

            val rate = rateCurrency.rate

            val scale = rateCurrency.currency.scale

            textViewName.text = rate.currencyCode
            textViewAmount.text = rateCurrency.getAmountString()

            val visibleDiff = rate.sell_diff != 0.0 || rate.buy_diff != 0.0

            textViewBuy.text = Currency.rateForUI(rate.sell, scale)
            bindDiff(textViewBuyDiff, rate.sell_diff, scale, visibleDiff)

            textViewSell.text = Currency.rateForUI(rate.buy, scale)
            bindDiff(textViewSellDiff, rate.buy_diff, scale, visibleDiff)

            textViewNb.text = Currency.rateForUI(rate.nb, scale)
            bindDiff(textViewNbDiff, rate.nb_diff, scale)

            if (rate.bcse_date == null
                    || rate.bcse_date != rate.nb_date && rate.nb == rate.bcse_rate
                    || rate.bcse_date == rate.nb_date && rate.bcse_diff == 0.0) { //todo experimental

                textViewBCSERoot.gone()

//                bindDiff(textViewNbDiff, rate.nb_diff, scale)

                textNbDate.visibleOrGone(rate.nb_date != null)
                textNbDate.text = String.format(Locale("ru"), "%1\$tb %1\$te", rate.nb_date)

            } else {

//                textViewNbDiff.gone()
                textNbDate.gone()

                textViewBCSERoot.visible()

                textViewBCSEDate.text = String.format(Locale("ru"), itemView.context.getString(R.string.BCSE_date), rate.bcse_date)
                textViewBCSERate.text = Currency.rateForUI(rate.bcse_rate, scale)

                bindDiff(textViewBCSEDiff, rate.bcse_diff, scale)

            }

            Picasso.with(itemView.context)
                    .load(rateCurrency.currency.flag)
                    .placeholder(R.drawable.ic_currency_default)
                    .into(imageViewCurrencyFlag)

        }

        private fun bindDiff(textView: TextView, diff: Double, scale: Int, visible: Boolean = diff != 0.0) {
            val color = when {
                diff == 0.0 -> android.R.attr.textColorSecondary
                diff > 0 -> R.attr.colorGreen
                else -> R.attr.colorRed
            }
            textView.setTextColor(context.getDefaultThemeColor(color))
            textView.text = Currency.rateDiffForUI(diff, scale)
            textView.visibleOrGone(visible)
        }
    }
}
