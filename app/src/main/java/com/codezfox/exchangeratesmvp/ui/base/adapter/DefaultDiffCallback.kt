package com.codezfox.exchangeratesmvp.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil


open class DefaultDiffCallback : DiffUtil.ItemCallback<DisplayableItem>() {
    override fun areItemsTheSame(
        oldItem: DisplayableItem,
        newItem: DisplayableItem
    ): Boolean {
        return if (oldItem.javaClass.name != newItem.javaClass.name) {
            false
        } else {
            oldItem.areItemsTheSame() == newItem.areItemsTheSame()
        }
    }

    override fun areContentsTheSame(
        oldItem: DisplayableItem,
        newItem: DisplayableItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: DisplayableItem, newItem: DisplayableItem): Any? {
        return newItem
    }
}
