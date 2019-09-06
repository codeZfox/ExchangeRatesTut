package com.niveler.numpad

import android.content.Context
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import com.codezfox.exchangeratesmvp.R

class NumPadView : LinearLayout {

    var editChange: ((String) -> Unit)? = null
    var value = ""
        set(value) {
            field = value
            editChange?.invoke(value)
        }

    val numPadAdapter = NumPadAdapter(ButtonNum.list2(), ::onClickNumPad, ::onLongClickNumPad, ::onPlusMinus)

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        val recyclerView = RecyclerView(context)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = this.numPadAdapter
        addView(recyclerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val outValue = TypedValue()
            context.getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
            //            btnDel.background = getDrawable(context, outValue.resourceId)
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
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