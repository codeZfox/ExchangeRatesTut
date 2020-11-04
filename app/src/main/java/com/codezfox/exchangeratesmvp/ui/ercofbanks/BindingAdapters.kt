package com.codezfox.exchangeratesmvp.ui.ercofbanks

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
            //todo maybe bold, and api 21
            view.setTextColor(view.context.getDefaultThemeColor(android.R.attr.colorAccent))
        } else {
            view.setTextColor(view.context.getDefaultThemeColor(android.R.attr.textColorSecondary))
        }
    }

}