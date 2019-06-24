package com.codezfox.paginator.screen

import android.support.v7.util.DiffUtil
import me.drakeet.multitype.MultiTypeAdapter


fun MultiTypeAdapter.showProgress(isVisible: Boolean) {
    val currentProgress = isProgress()

    if (isVisible && !currentProgress) {
        addItem(ProgressItem())
    } else if (!isVisible && currentProgress) {
        removeLastItem()
    }
}

private fun MultiTypeAdapter.isProgress() = items.isNotEmpty() && items.last() is ProgressItem

private fun MultiTypeAdapter.isRetry() = items.isNotEmpty() && items.last() is RetryItem

fun MultiTypeAdapter.showRetry(isVisible: Boolean, errorMessage: String = "", callback: (() -> Unit)? = null) {
    val currentProgress = isRetry()

    if (isVisible && !currentProgress) {
        addItem(RetryItem(errorMessage, callback))
    } else if (!isVisible && currentProgress) {
        removeLastItem()
    }
}


fun MultiTypeAdapter.addItem(item: Any) {
    val newItems = listOf(*items.toTypedArray(), item)
    updateAdapter(newItems)
}


fun MultiTypeAdapter.removeLastItem() {
    val newItems = items.toMutableList()

    val lastIndex = newItems.lastIndex
    newItems.removeAt(lastIndex)

    updateAdapter(newItems)
}

interface Identifier {
    fun areItemsTheSame(): Any
}

fun MultiTypeAdapter.updateAdapter(newItems: List<*>) {
    if (items.isEmpty()) {
        items = newItems.toMutableList()
        notifyItemRangeInserted(0, newItems.size)
    } else {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return items.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = items[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return if (oldItem is Identifier && newItem is Identifier)
                    oldItem.areItemsTheSame() == newItem.areItemsTheSame()
                else {
                    oldItem == newItem
                }
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }
        })
        items = newItems.toMutableList()
        result.dispatchUpdatesTo(this)
    }
}
