package com.codezfox.exchangeratesmvp.ui.ercofbanks

import android.databinding.BindingAdapter
import android.widget.TextView
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.extensions.getDefaultThemeColor

object BindingAdapters {

    @BindingAdapter("sortTypeBuy")
    @JvmStatic
    fun sortTypeBuy(view: TextView, sort: RateCurrencySort) {
        bindSort(view, sort == RateCurrencySort.BUY)
    }

    @BindingAdapter("sortTypeSell")
    @JvmStatic
    fun sortTypeSell(view: TextView, sort: RateCurrencySort) {
        bindSort(view, sort == RateCurrencySort.SELL)
    }

    private fun bindSort(view: TextView, isSelect: Boolean) {
        if (isSelect) {
            view.setTextColor(view.resources.getColor(R.color.colorRed))
        } else {
            view.setTextColor(view.context.getDefaultThemeColor(android.R.attr.textColorSecondary))
        }
    }

}