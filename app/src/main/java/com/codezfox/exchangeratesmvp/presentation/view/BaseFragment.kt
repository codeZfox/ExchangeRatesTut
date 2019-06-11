package com.codezfox.exchangeratesmvp.presentation.view

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatFragment
import com.codezfox.exchangeratesmvp.extensions.get
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.on
import ru.terrakok.cicerone.Router

open class BaseFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    val kodeinActivity by lazy { kodein.on(activity) }

    fun getRouter() = kodeinActivity.get<Router>()

}


open class BaseMvpFragment : MvpAppCompatFragment(), KodeinAware {

    override val kodein by kodein()

    val kodeinActivity by lazy { kodein.on(activity) }

    fun getRouter() = kodeinActivity.get<Router>()

}