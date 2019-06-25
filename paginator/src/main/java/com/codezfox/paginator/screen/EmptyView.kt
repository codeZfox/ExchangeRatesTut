package com.codezfox.paginator.screen

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatImageView
import android.view.ViewGroup
import android.widget.TextView
import com.codezfox.extensions.gone
import com.codezfox.extensions.setHeight
import com.codezfox.extensions.visible
import com.codezfox.paginator.R


interface EmptyView {

    fun getView(): ViewGroup

    fun setHeight(h: Int) {

    }

    fun show()

    fun hide()
}

class EmptyViewHolder(

        private val view: ViewGroup,

        @DrawableRes
        var imageResId: Int? = R.drawable.ic_box,

        @StringRes
        var textResId: Int = R.string.data_not_found

) : EmptyView {

    private val imageView: AppCompatImageView = view.findViewById(R.id.zvh_imageView)
    private val textView: TextView = view.findViewById(R.id.zvh_textView)

    override fun getView(): ViewGroup {
        return view
    }

    override fun setHeight(h: Int) {
        view.setHeight(h)
    }

    override fun show() {

        val imageResId = imageResId
        if (imageResId != null) {
            imageView.setImageResource(imageResId)
            imageView.visible()
        } else {
            imageView.gone()
        }

        textView.setText(textResId)

        view.visible()
    }

    override fun hide() {
        view.gone()
    }
}
