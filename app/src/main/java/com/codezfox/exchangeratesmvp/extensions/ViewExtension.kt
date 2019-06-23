package com.codezfox.exchangeratesmvp.extensions

import android.content.res.Resources
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View


fun View.onClick(l: (v: View?) -> Unit) {
    setOnClickListener(l)
}

fun View.onLongClick(l: (v: android.view.View?) -> Boolean) {
    setOnLongClickListener(l)
}

fun View.visibleOrGone(visibility: Boolean) {
    if (visibility) this.visible() else this.gone()
}

fun View.visibleOrInvisible(visibility: Boolean) {
    if (visibility) this.visible() else this.invisible()
}

fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

inline val RecyclerView.ViewHolder.context
    get() = this.itemView.context!!

inline val RecyclerView.ViewHolder.resources: Resources
    get() = this.itemView.context!!.resources

fun SwipeRefreshLayout.isRefreshing(refresh: Boolean) {
    if (refresh) {
        if (!isRefreshing) post {
            isRefreshing = true
        }
    } else {
        this.post { this.isRefreshing = refresh }
    }
}


fun View.setHeight(height: Int) {
    this.layoutParams = this.layoutParams.apply {
        this.height = height
    }
}

fun View.setWidth(width: Int) {
    this.layoutParams = this.layoutParams.apply {
        this.width = width
    }
}

fun View.setImageSize(height: Int, width: Int) {
    this.layoutParams = this.layoutParams.apply {
        this.height = height
        this.width = width
    }
}