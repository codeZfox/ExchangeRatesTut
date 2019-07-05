package com.codezfox.paginator.screen

import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatImageView
import android.view.ViewGroup
import android.widget.TextView
import com.codezfox.extensions.*
import com.codezfox.paginator.R


interface ErrorView {

    fun getView(): ViewGroup

    fun setHeight(h: Int) {

    }

    fun showError(imageId: Int? = null,
                  msg: String? = null,
                  buttonText: String? = null,
                  onClick: (() -> Unit)? = null)

    fun hide()
}

class ErrorViewHolder(

        private val view: ViewGroup,

        @DrawableRes
        var defaultImageResId: Int? = R.drawable.ic_scan,

        var defaultButtonName: String? = view.context.getString(R.string.repeat)

) : ErrorView {

    private val imageView: AppCompatImageView = view.findViewById(R.id.zvh_imageView)
    private val textView: TextView = view.findViewById(R.id.zvh_textView)
    private val button: TextView = view.findViewById(R.id.zvh_button)


    override fun getView(): ViewGroup {
        return view
    }

    override fun setHeight(h: Int) {
        view.setHeight(h)
    }

    override fun showError(imageId: Int?,
                           msg: String?,
                           buttonText: String?,
                           onClick: (() -> Unit)?) {
        showErrorDefault(imageId ?: defaultImageResId, msg,
                         buttonText ?: defaultButtonName, onClick)
    }

    private fun showErrorDefault(imageId: Int?,
                                 msg: String?,
                                 buttonText: String?,
                                 onClick: (() -> Unit)?) {

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

    override fun hide() {
        view.gone()
    }
}