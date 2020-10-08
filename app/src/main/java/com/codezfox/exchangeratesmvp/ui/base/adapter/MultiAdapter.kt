package com.codezfox.exchangeratesmvp.ui.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


open class MultiAdapter(
    diffCallback: DiffUtil.ItemCallback<DisplayableItem> = DefaultDiffCallback(),
    private val onCurrentListChanged: ((previousList: List<DisplayableItem>, currentList: List<DisplayableItem>) -> Unit)? = null
) : ListAdapter<DisplayableItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCurrentListChanged(previousList: List<DisplayableItem>, currentList: List<DisplayableItem>) {
        onCurrentListChanged?.invoke(previousList, currentList)
    }

    private val binders: MutableList<Pair<Class<*>, ItemViewBinder<*, *>>> = mutableListOf()

    public override fun getItem(position: Int): DisplayableItem {
        return super.getItem(position)
    }

    fun <T : DisplayableItem, VH : RecyclerView.ViewHolder> register(
        clazz: Class<T>,
        binder: ItemViewBinder<T, VH>
    ) {
        binders.add(clazz to binder)
    }

    override fun getItemViewType(position: Int): Int {
        return binders.indexOfFirst { (binderClass, _) -> checkEqualsClass(getItem(position).javaClass, binderClass) }
    }

    private fun checkEqualsClass(javaClass: Class<in Nothing>, binderClass: Class<*>): Boolean {
        return javaClass == binderClass
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return binders[viewType].second.onCreateViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val javaClass = getItem(position).javaClass
        binders.find { (binderClass, _) -> checkEqualsClass(javaClass, binderClass) }!!.second.let {
            it as ItemViewBinder<DisplayableItem, RecyclerView.ViewHolder>
            it.onBindViewHolder(holder, getItem(position))
        }
    }
}