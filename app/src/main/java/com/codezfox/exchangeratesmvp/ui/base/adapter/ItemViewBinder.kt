package com.codezfox.exchangeratesmvp.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class ItemViewBinder<T : DisplayableItem, VH : RecyclerView.ViewHolder> {
    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH
    abstract fun onBindViewHolder(holder: VH, item: T)
}