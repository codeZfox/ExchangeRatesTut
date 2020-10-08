package com.codezfox.exchangeratesmvp.ui.base

import android.databinding.BindingAdapter
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

object DataBindingAdapters {
//    @BindingAdapter("android:src")
//    fun setImageUri(view: ImageView, imageUri: String?) {
//        if (imageUri == null) {
//            view.setImageURI(null)
//        } else {
//            view.setImageURI(Uri.parse(imageUri))
//        }
//    }
//
//    @BindingAdapter("android:src")
//    fun setImageUri(view: ImageView, imageUri: Uri?) {
//        view.setImageURI(imageUri)
//    }
//
//    @BindingAdapter("android:src")
//    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
//        view.setImageDrawable(drawable)
//    }
//
//    @JvmStatic
//    @BindingAdapter("android:src")
//    fun setImageResource(imageView: ImageView, resource: Int?) {
//        imageView.setImageResource(resource ?: 0)
//    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view: TextView, resource: Int?) {
        if (resource != null) {
            view.setText(resource)
        } else {
            view.text = ""
        }
    }

}

//@BindingMethods(
//    BindingMethod(type = TextView::class, attribute = "android:text", method = "setText")
//)
object ImageViewBindingAdapter {

}