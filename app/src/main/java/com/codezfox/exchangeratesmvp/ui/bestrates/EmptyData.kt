package com.codezfox.exchangeratesmvp.ui.bestrates

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.codezfox.exchangeratesmvp.R

class EmptyData(

    @DrawableRes
        var imageResId: Int = R.drawable.ic_box,

    @StringRes
        var textResId: Int? = R.string.data_not_found

)

class ErrorData(

        @DrawableRes
        var imageResId: Int? = R.drawable.ic_scan,

        var buttonName: Int? = R.string.repeat,

        var description: String? = null,

        val onClick: RetryListener = object : RetryListener {
                override fun invoke() {

                }
        }

)