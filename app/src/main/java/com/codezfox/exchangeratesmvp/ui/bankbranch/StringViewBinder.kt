package com.codezfox.exchangeratesmvp.ui.bankbranch

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.ui.base.StringResources
import com.codezfox.exchangeratesmvp.ui.base.adapter.DisplayableItem
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import com.codezfox.exchangeratesmvp.ui.base.setText
import com.codezfox.extensions.gone
import com.codezfox.extensions.onClick
import com.codezfox.extensions.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_string.*


data class DataString(val data: StringResources, val type: TYPE) : DisplayableItem {

    override fun areItemsTheSame(): String {
        return type.name
    }

    enum class TYPE(@DrawableRes val imageId: Int?) {
        STRING(null),
        ADDRESS(R.drawable.ic_city),
        PHONE(R.drawable.ic_face_agent),
        SERVICES(R.drawable.ic_bank)
    }
}

class StringViewBinder(private val onClick: (string: DataString) -> Unit) : ItemViewBinder<DataString, StringViewBinder.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindView(dataString: DataString) {

            val imageId = dataString.type.imageId

            if (imageId != null) {
                imageView.setImageResource(imageId)
                imageView.visible()
            } else {
                imageView.gone()
            }

            textView.setText(dataString.data)

        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_string, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DataString) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
    }

}
