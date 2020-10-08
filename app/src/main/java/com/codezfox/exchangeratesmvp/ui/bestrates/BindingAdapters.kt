package com.codezfox.exchangeratesmvp.ui.bestrates

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @BindingAdapter("lastDate")
    @JvmStatic
    fun bindLastDate(view: TextView, date: Date?) {
        if (date != null) {
            val simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
            view.text = "Последнее обновление: " + simpleDateFormat.format(date)
        } else {
            view.text = "null"
        }
    }

}