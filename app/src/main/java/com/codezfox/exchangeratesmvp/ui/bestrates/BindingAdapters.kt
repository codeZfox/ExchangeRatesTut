package com.codezfox.exchangeratesmvp.ui.bestrates

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @BindingAdapter("lastDate")
    @JvmStatic
    fun bindLastDate(view: TextView, date: Date?) {
        if (date != null) {
            val simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale("ru"))
            view.text = "Последнее обновление: " + simpleDateFormat.format(date)
        } else {
            view.text = ""
        }
    }

}