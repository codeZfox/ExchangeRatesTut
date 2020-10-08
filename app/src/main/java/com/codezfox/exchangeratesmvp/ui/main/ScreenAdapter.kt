package com.codezfox.exchangeratesmvp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ScreenAdapter(private val screens: List<SupportAppScreen>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return screens.size
    }

    override fun getItem(position: Int): Fragment {
        return screens[position].fragment
    }

}