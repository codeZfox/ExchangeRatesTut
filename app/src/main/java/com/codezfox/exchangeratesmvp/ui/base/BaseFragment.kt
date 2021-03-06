package com.codezfox.exchangeratesmvp.ui.base

import androidx.fragment.app.Fragment
import com.codezfox.exchangeratesmvp.extensions.get
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.on
import ru.terrakok.cicerone.Router

//todo check
open class BaseFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    val kodeinActivity by lazy { kodein.on(activity) }

    fun getRouter() = kodeinActivity.get<Router>()

}
