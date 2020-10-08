package com.codezfox.exchangeratesmvp.ui.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.databinding.ScreenConverterBinding
import com.codezfox.exchangeratesmvp.ui.base.BaseMvvmFragment
import com.codezfox.exchangeratesmvp.ui.base.adapter.MultiAdapter
import com.codezfox.exchangeratesmvp.ui.base.viewModelLazyInstance
import kotlinx.android.synthetic.main.screen_converter.*
import ru.terrakok.cicerone.Router

class ConverterFragment : BaseMvvmFragment<ConverterViewModel, Router>() {

    override val viewModel: ConverterViewModel by viewModelLazyInstance()

    private val adapter by lazy {
        MultiAdapter().also { adapter ->
            adapter.register(ConverterRate::class.java, ConverterViewBinder {
                viewModel.selectCurrency(it) //todo maybe
                val string = it.summary
                numPad.value = string
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ScreenConverterBinding.inflate(inflater).also { binding ->
            binding.viewModel = viewModel
            binding.adapter = adapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }//todo maybe

        numPad.editChange = { text ->
            viewModel.input(text) //todo maybe
            Log.d("input", "$text")
        }
    }

}