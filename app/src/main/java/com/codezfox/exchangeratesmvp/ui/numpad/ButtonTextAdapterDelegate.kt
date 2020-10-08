package com.niveler.numpad

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.item_button_num.view.*


class ButtonTextAdapterDelegate(

        private val count: Int,
        private val onClick: ((button: Button) -> Unit)?,
        private val onLongClick: ((button: Button) -> Unit)?

) : AdapterDelegate<List<Button>>() {

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button_num, parent, false)
        view.layoutParams.height = parent.height / count * 3
        return ViewHolder(view)
    }

    override fun isForViewType(items: List<Button>, position: Int): Boolean {
        return items[position] is ButtonText
    }

    override fun onBindViewHolder(
            items: List<Button>,
            position: Int,
            holder: RecyclerView.ViewHolder,
            payloads: MutableList<Any>
    ) {
        val vh = holder as ViewHolder

        val button = items[position] as ButtonText

        vh.itemView.textNum.text = button.toString()
        vh.itemView.textLetters.visibility = View.GONE

//        val colorLayout = holder.itemView.context.resources.getColor(R.color.color_gray_light)
//
//        vh.itemView.buttonCardView.setCardBackgroundColor(colorLayout)

        vh.itemView.setOnClickListener {
            onClick?.invoke(items[holder.layoutPosition])
        }
        vh.itemView.setOnLongClickListener {
            onLongClick?.invoke(items[holder.layoutPosition])
            true
        }
    }


    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val outValue = TypedValue()
                itemView.context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                itemView.buttonCardView.foreground =
                        itemView.context.getDrawable(outValue.resourceId)
            }
        }
    }
}