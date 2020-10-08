package com.codezfox.exchangeratesmvp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.codezfox.exchangeratesmvp.R
import com.codezfox.exchangeratesmvp.Screens
import com.codezfox.exchangeratesmvp.ui.base.BackAware
import kotlinx.android.synthetic.main.layout_tab.view.*
import kotlinx.android.synthetic.main.screen_main.*


class MainFragment : Fragment(), BackAware {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.screen_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpager.adapter = ScreenAdapter(listOf(Screens.BestRates(), Screens.Converter()), this.childFragmentManager)
        val inflater = LayoutInflater.from(view.context)
        val resources = view.context.resources
        tabLayout.setCustomTabView { container, position, adapter ->
            return@setCustomTabView inflater.inflate(R.layout.layout_tab, container, false)
                .also { icon ->
                    when (position) {
                        0 -> icon.image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_bank, view.context.theme))
                        1 -> icon.image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_calculator, view.context.theme))
                        else -> throw IllegalStateException("Invalid position: $position")
                    }
                }
        }

        //todo refactoring
        fun selectTab(position: Int) {
            tabLayout.getTabAt(0)?.image?.setColorFilter(ContextCompat.getColor(view.context, if (position == 0) R.color.colorPrimary else R.color.colorGray))
            tabLayout.getTabAt(1)?.image?.setColorFilter(ContextCompat.getColor(view.context, if (position == 1) R.color.colorPrimary else R.color.colorGray))
        }

        tabLayout.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                selectTab(position)
            }

        })

        tabLayout.setViewPager(viewpager)
        selectTab(0)

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