package com.codezfox.exchangeratesmvp.presentation.paginator.screen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.extensions.onClick

import me.drakeet.multitype.ItemViewBinder


class RetryViewBinder : ItemViewBinder<RetryItem, RetryViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_retry, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, retry: RetryItem) {
        with(holder){
            reloadButton.onClick {
                retry.callback?.invoke()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reloadButton: Button = itemView.findViewById(R.id.ir_reload)
    }
}
