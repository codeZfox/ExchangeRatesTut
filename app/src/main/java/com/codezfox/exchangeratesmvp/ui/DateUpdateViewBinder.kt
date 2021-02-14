package com.codezfox.exchangeratesmvp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import com.codezfox.exchangeratesmvp.ui.bestrates.BindingAdapters
import com.codezfox.extensions.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_last_date_data.*
import java.util.*


data class DateUpdate(val date: Date):DisplayableItem{
    override fun areItemsTheSame(): String {
        return date.time.toString()
    }
}

class DateUpdateViewBinder : ItemViewBinder<DateUpdate, DateUpdateViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(dateUpdate: DateUpdate) {
            textViewLastDateData.visible()
            BindingAdapters.bindLastDate(textViewLastDateData, dateUpdate.date) // todo move to binding
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_last_date_data, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DateUpdate) {
        holder.bindView(item)
    }

}
