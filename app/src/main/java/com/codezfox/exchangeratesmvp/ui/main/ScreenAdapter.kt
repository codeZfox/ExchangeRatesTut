package com.codezfox.exchangeratesmvp.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ScreenAdapter(private val screens: List<SupportAppScreen>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return screens.size
    }

    override fun getItem(position: Int): Fragment {
        return screens[position].fragment
    }

}