package com.codezfox.exchangeratesmvp.ui.base.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


open class MultiAdapter(diffCallback: DiffUtil.ItemCallback<DisplayableItem> = DefaultDiffCallback()) : ListAdapter<DisplayableItem, RecyclerView.ViewHolder>(diffCallback) {

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