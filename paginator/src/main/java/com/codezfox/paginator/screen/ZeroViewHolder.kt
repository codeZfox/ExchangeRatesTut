package com.codezfox.paginator.screen

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatImageView
import android.view.ViewGroup
import android.widget.TextView
import com.codezfox.extensions.*
import com.codezfox.paginator.R

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


    @DrawableRes
    var imageResId: Int? = R.drawable.ic_scan

    fun showEmptyError(imageId: Int? = imageResId,
                       msg: String? = null,
                       buttonText: String? = view.context.getString(R.string.repeat),
                       onClick: (() -> Unit)? = null) {

        if (imageId != null) {
            imageView.setImageResource(imageId)
            imageView.visible()
        } else {
            imageView.gone()
        }

        if (msg != null) {
            textView.text = msg
            textView.visible()
        } else {
            textView.gone()
        }

        if (buttonText != null) {
            button.text = buttonText
            button.visible()
            if (onClick != null) {
                button.setOnClickListener { onClick.invoke() }
            }
        } else {
            button.invisible()
        }

        view.visible()
    }

    fun hide() {
        view.gone()
    }
}