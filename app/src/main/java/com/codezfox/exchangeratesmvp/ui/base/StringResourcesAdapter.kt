package com.codezfox.exchangeratesmvp.ui.base

import android.widget.TextView
import androidx.databinding.BindingAdapter

class StringResourcesAdapter {

    object BindingAdapters {
        @BindingAdapter("app:text", requireAll = false)
        @JvmStatic
        fun text(view: TextView, text: StringResources?) {
            if (text != null) {
                view.text = view.getString(text)
            }
        }
    }
}