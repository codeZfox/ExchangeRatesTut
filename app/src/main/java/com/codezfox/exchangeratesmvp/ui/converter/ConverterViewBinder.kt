package com.codezfox.exchangeratesmvp.ui.converter


import android.graphics.Typeface
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.ui.base.adapter.ItemViewBinder
import com.codezfox.extensions.context
import com.codezfox.extensions.onClick
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_converter.*


class ConverterViewBinder(private val onClick: (exchangeRate: ConverterRate) -> Unit) : ItemViewBinder<ConverterRate, ConverterViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_converter, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ConverterRate) {
        holder.bindView(item)
        holder.itemView.onClick {
            onClick.invoke(item)
        }
        val mDetector = GestureDetectorCompat(holder.context, SimpleOnGestureListener()).apply {
            setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    // This is where u add your OnClick event
                    onClick.invoke(item)
                    return false
                }

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    return false
                }

                override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                    return false
                }
            })
        }

        holder.textViewRate.setOnTouchListener { _, event ->
            mDetector.onTouchEvent(event)
            false
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(rate: ConverterRate) {


            val currency = rate.bestRateCurrency.currency

            textViewName.text = currency.id
//            textViewRate.text = Currency.rateForUI(rate.summary, scale)
            textViewRate.text = rate.summary
            textViewRate.setTypeface(null, if (rate.isSelected) Typeface.BOLD else Typeface.NORMAL)
            textViewRate.setTextSize(if (rate.isSelected) 22f else 18f)


            //todo image loading duplicate
            currency.getFlagDrawable().let { flagDrawable ->
                if (flagDrawable == null) {
                    currency.flag.let { flag ->
                        if (flag.isBlank()) {
                            imageViewCurrencyFlag.visibility = View.INVISIBLE
                        } else {
                            imageViewCurrencyFlag.visibility = View.VISIBLE
                            Picasso.with(itemView.context)
                                .load(flag)
                                .fit()
                                .centerCrop()
                                .placeholder(R.drawable.ic_currency_default)
                                .into(imageViewCurrencyFlag)

                        }
                    }
                } else {
                    imageViewCurrencyFlag.visibility = View.VISIBLE
                    Picasso.with(itemView.context)
                        .load(flagDrawable)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.ic_currency_default)
                        .into(imageViewCurrencyFlag)

                }
            }

        }
    }
}
