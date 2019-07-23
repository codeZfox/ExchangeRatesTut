package com.codezfox.exchangeratesmvp.ui.bankbranch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.extensions.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_string.*
import me.drakeet.multitype.ItemViewBinder


class DataString(val data: String)
class StringViewBinder(private val onClick: (string: String) -> Unit) : ItemViewBinder<String, StringViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(string: String) {
            textView.text = string
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_string, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

}
