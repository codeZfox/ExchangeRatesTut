package com.codezfox.exchangeratesmvp.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.codezfox.exchangeratesmvp.extensions.get
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.on
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Router

abstract class BaseMvvmFragment<VM : BaseMvvmViewModel<R>, R : BaseRouter> : Fragment(), KodeinAware {


    override val kodein by kodein()

    abstract val viewModel: VM

    val kodeinActivity by lazy { kodein.on(activity) }

    fun getRouter() = kodeinActivity.get<Router>()

    protected val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    fun Disposable.addDisposable() {
        disposables.add(this)
    }

    protected fun getChildCurrentFragmentByContainerId(@IdRes containerId: Int): Fragment? {
        return childFragmentManager.findFragmentById(containerId)
    }
}