package com.codezfox.exchangeratesmvp.ui.branch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.extensions.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_phone.*
import me.drakeet.multitype.ItemViewBinder


class PhoneData(val data: String)
class PhoneViewBinder(private val onClick: (string: PhoneData) -> Unit) : ItemViewBinder<PhoneData, PhoneViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(data: PhoneData) {
            textView.text = data.data
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_phone, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: PhoneData) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

}
