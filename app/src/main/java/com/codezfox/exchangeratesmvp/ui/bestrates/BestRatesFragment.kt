package com.codezfox.exchangeratesmvp.ui.bestrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.databinding.ScreenBestRatesBinding
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}