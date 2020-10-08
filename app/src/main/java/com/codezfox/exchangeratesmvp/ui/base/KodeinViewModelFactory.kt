@file:Suppress("UNCHECKED_CAST")

package com.codezfox.exchangeratesmvp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Router

inline fun <reified ViewModelT : BaseMvvmViewModel<R>, R : Router> BaseMvvmFragment<ViewModelT, R>.viewModelLazyInstance() =
    lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return this@viewModelLazyInstance.kodein.run {
                        val viewModel by instance<ViewModelT>()
                        viewModel
                    } as T
                }
            })
            .get(ViewModelT::class.java)
            .also {
                it.router = getRouter()
            }
    }

inline fun <reified ViewModelT : BaseMvvmViewModel<R>, R : Router> BaseMvvmFragment<ViewModelT, R>.viewModelInstance() =
    ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return this@viewModelInstance.kodein.run {
                    val viewModel by instance<ViewModelT>()
                    viewModel
                } as T
            }
        })
        .get(ViewModelT::class.java)
        .also {
            it.router = getRouter()
        }


inline fun <reified ViewModelT : BaseMvvmViewModel<R>, R : Router, reified Param> BaseMvvmFragment<ViewModelT, R>.viewModelLazyFactory(
    crossinline param: () -> Param
) =
    lazy {
        ViewModelProviders
            .of(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return this@viewModelLazyFactory.kodein.run {
                        val viewModelFactory: (Param) -> ViewModelT by factory()
                        viewModelFactory(param())
                    } as T
                }
            })
            .get(ViewModelT::class.java)
            .also {
                it.router = getRouter()
            }
    }

inline fun <reified ViewModelT : BaseMvvmViewModel<R>, R : Router> BaseMvvmFragment<ViewModelT, R>.viewModelLazy(
    crossinline viewModel: () -> ViewModelT
) =
    lazy {
        ViewModelProviders
            .of(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModel() as T
                }
            })
            .get(ViewModelT::class.java)
            .also {
                it.router = getRouter()
            }
    }

