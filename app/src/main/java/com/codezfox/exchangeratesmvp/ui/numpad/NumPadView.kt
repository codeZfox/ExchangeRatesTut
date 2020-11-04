package com.niveler.numpad

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codezfox.exchangeratesmvp.R

class NumPadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    var editChange: ((String) -> Unit)? = null
    var value = ""
        set(value) {
            field = value
            editChange?.invoke(value)
        }

    val numPadAdapter = NumPadAdapter(ButtonNum.list2(), ::onClickNumPad, ::onLongClickNumPad, ::onPlusMinus)

    init {
        overScrollMode = OVER_SCROLL_NEVER
        layoutManager = GridLayoutManager(context, 3)
        adapter = NumPadAdapter(ButtonNum.list2(), ::onClickNumPad, ::onLongClickNumPad, ::onPlusMinus)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val outValue = TypedValue()
            context.theme.resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
            //            btnDel.background = getDrawable(context, outValue.resourceId)
        }
    }

//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_numpad)
//
//        btnDel.setOnClickListener {
//            val length = value.symbol.length
//            if (length > 0) {
//                value.symbol = value.symbol.substring(0, length - 1)
//            }
//        }
//
//        btnDel.setOnLongClickListener {
//            val length = value.symbol.length
//            if (length > 0) {
//                value.symbol = ""
//            }
//            true
//        }
//
//
//        value.symbol = value

//}

    private fun onPlusMinus() {
        val string = value
        value = if (string.startsWith('-')) {
            string.removePrefix("-")
        } else {
            "-$string"
        }
    }

    private fun onClickNumPad(it: Button) {
        value = it.process.invoke(value)
    }

    private fun onLongClickNumPad(it: Button) {
        value = it.process2.invoke(value)
    }

}