package com.codezfox.exchangeratesmvp.presentation.view

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

open class BaseFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

}

open class BaseMvpFragment : MvpAppCompatFragment(), KodeinAware {

    override val kodein by kodein()

}