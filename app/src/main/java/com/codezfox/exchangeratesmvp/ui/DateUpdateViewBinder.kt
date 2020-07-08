package com.codezfox.exchangeratesmvp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.extensions.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_last_date_data.*
import me.drakeet.multitype.ItemViewBinder
import java.text.SimpleDateFormat
import java.util.*


data class DateUpdate(val date: Date)

class DateUpdateViewBinder : ItemViewBinder<DateUpdate, DateUpdateViewBinder.ViewHolder>() {


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        var simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

        fun bindView(dateUpdate: DateUpdate) {
            textViewLastDateData.visible()
            textViewLastDateData.text = "Последнее обновление: " + simpleDateFormat.format(dateUpdate.date)
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_last_date_data, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DateUpdate) {
        holder.bindView(item)
    }

}