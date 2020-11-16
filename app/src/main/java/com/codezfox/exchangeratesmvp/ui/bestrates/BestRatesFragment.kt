package com.codezfox.exchangeratesmvp.ui.bestrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.databinding.ScreenBestRatesBinding
import com.codezfox.exchangeratesmvp.extensions.getDefaultThemeColor
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmFragment
import com.codezfox.exchangeratesmvp.ui.base.adapter.MultiAdapter
import com.codezfox.exchangeratesmvp.ui.base.viewModelLazyInstance
import kotlinx.android.synthetic.main.screen_best_rates.*
import ru.terrakok.cicerone.Router


class BestRatesFragment : BaseMvvmFragment<BestRatesViewModel, Router>() {

    override val viewModel: BestRatesViewModel by viewModelLazyInstance()

    private val adapter by lazy {
        MultiAdapter().also { adapter ->
            adapter.register(BestRateCurrency::class.java, BestRatesViewBinder {
                viewModel.openCurrency(it)
            })
        }
    }

    private var binding: ScreenBestRatesBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ScreenBestRatesBinding.inflate(inflater).also { binding ->
            this.binding = binding
            binding.viewModel = viewModel
            binding.adapter = adapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        swipeToRefresh.applyColorsToSwipeToRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}

fun SwipeRefreshLayout.applyColorsToSwipeToRefresh() { //todo move to file

    fun getProgressBackground(@ColorInt color: Int): Int {

        fun i(color: Int) = (color + 4).let {
            when {
                it < 10 -> 0
                it > 255 -> 255
                else -> it
            }
        }

        val a = i(color shr 24 and 0xff)
        val r = i(color shr 16 and 0xff)
        val g = i(color shr 8 and 0xff)
        val b = color and 0xff
        return a and 0xff shl 24 or (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }

    this.setColorSchemeColors(context.getDefaultThemeColor(R.attr.colorAccent))
    this.setProgressBackgroundColorSchemeColor(getProgressBackground(context.getDefaultThemeColor(android.R.attr.windowBackground)))
}