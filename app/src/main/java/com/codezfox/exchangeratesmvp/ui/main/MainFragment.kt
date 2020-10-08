package com.codezfox.exchangeratesmvp.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.ui.base.BackAware
import kotlinx.android.synthetic.main.screen_main.*


class MainFragment : Fragment(), BackAware {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager.adapter = ScreenAdapter(listOf(Screens.BestRates(), Screens.Converter()), this.childFragmentManager)
        tabLayout.setupWithViewPager(viewpager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_bank)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_calculator)
    }

    override fun onBackPressed(): Boolean {
        return if (viewpager.currentItem == 0) {
            false
        } else {
            viewpager.currentItem = viewpager.currentItem - 1
            true
        }
    }

}