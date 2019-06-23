package com.codezfox.exchangeratesmvp.presentation.view.currencyrates


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.domain.models.Currency
import com.codezfox.exchangeratesmvp.domain.models.RateCurrency
import com.codezfox.exchangeratesmvp.extensions.*
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_rate.*
import me.drakeet.multitype.ItemViewBinder
import java.util.*


class RateCurrencyViewBinder(val onClick: (rateCurrency: RateCurrency) -> Unit) : ItemViewBinder<RateCurrency, RateCurrencyViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_currency_rate, parent, false)
        return ViewHolder(onClick, root)
    }

    override fun onBindViewHolder(holder: ViewHolder, rateCurrency: RateCurrency) {
        holder.bindView(rateCurrency)
    }

    class ViewHolder(val onClick: (rateCurrency: RateCurrency) -> Unit, override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(rateCurrency: RateCurrency) {

            containerView.onClick {
                onClick.invoke(rateCurrency)
            }

            val rate = rateCurrency.rate

            val scale = rateCurrency.currency.scale

            textViewName.text = rate.currencyCode
            textViewAmount.text = rateCurrency.getAmountString()
            textViewBuy.text = Currency.rateForUI(rate.sell, scale)
            textViewSell.text = Currency.rateForUI(rate.buy, scale)
            textViewNb.text = Currency.rateForUI(rate.nb, scale)

            if (rate.bcse_date == null || rate.bcse_date != rate.nb_date && rate.nb == rate.bcse_rate) {

                textViewBCSERoot.gone()

                textViewNbDiff.visibleOrInvisible(rate.nb_diff != 0.0)
                textViewNbDiff.setTextColor(resources.getColor(if (rate.nb_diff >= 0) R.color.colorGreen else R.color.colorRed))
                textViewNbDiff.text = Currency.rateDiffForUI(rate.nb_diff, scale)

            } else {

                textViewNbDiff.gone()

                textViewBCSERoot.visible()

                textViewBCSEDate.text = String.format(Locale("ru"), itemView.context.getString(R.string.BCSE_date), rate.bcse_date)
                textViewBCSERate.text = Currency.rateForUI(rate.bcse_rate, scale)

                textViewBCSEDiff.visibleOrInvisible(rate.bcse_diff != 0.0)
                textViewBCSEDiff.text = Currency.rateDiffForUI(rate.bcse_diff, scale)
                textViewBCSEDiff.setTextColor(resources.getColor(if (rate.bcse_diff >= 0) R.color.colorGreen else R.color.colorRed))

            }

            Picasso.with(itemView.context)
                    .load(rateCurrency.currency?.flag)
                    .placeholder(R.drawable.ic_currency_default)
                    .into(imageViewCurrencyFlag)

        }
    }
}
