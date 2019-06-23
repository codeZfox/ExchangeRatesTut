package com.codezfox.exchangeratesmvp.presentation.paginator.screen

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatImageView
import android.view.ViewGroup
import android.widget.TextView
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.extensions.gone
import com.codezfox.exchangeratesmvp.extensions.invisible
import com.codezfox.exchangeratesmvp.extensions.setHeight
import com.codezfox.exchangeratesmvp.extensions.visible

class EmptyViewHolder(
        private val view: ViewGroup
) {

    private val imageView: AppCompatImageView = view.findViewById(R.id.zvh_imageView)
    private val textView: TextView = view.findViewById(R.id.zvh_textView)

    @DrawableRes
    var imageResId: Int? = R.drawable.ic_box

    @StringRes
    var textResId: Int = R.string.data_not_found

//    @ColorRes
//    var colorResId: Int = R.color.colorTextLightPlaceHolder

    fun setHeight(h: Int) {
        view.setHeight(h)
    }

    fun showEmptyData() {

        val imageResId = imageResId
        if (imageResId != null) {
            imageView.setImageResource(imageResId)
            imageView.visible()
        } else {
            imageView.gone()
        }

        textView.setText(textResId)
//        textView.setTextColor(ContextCompat.getColor(view.context, colorResId))

        view.visible()
    }

    fun hide() {
        view.gone()
    }
}

class ErrorViewHolder(
        private val view: ViewGroup
) {

    fun setHeight(h: Int) {
        view.setHeight(h)
    }

    private val imageView: AppCompatImageView = view.findViewById(R.id.zvh_imageView)
    private val textView: TextView = view.findViewById(R.id.zvh_textView)
    private val button: TextView = view.findViewById(R.id.zvh_button)

    fun showEmptyError(imageId: Int?, msg: String, onClick: (() -> Unit)? = null) {

//        imageId?.let { imageView.setImageResource(it) }
        textView.text = msg

        if (onClick != null) {
            button.visible()
            button.setOnClickListener { onClick.invoke() }
        } else {
            button.invisible()
        }

        view.visible()
    }

    fun hide() {
        view.gone()
    }
}