package com.codezfox.extensions

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.MvpViewState
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast


@StateStrategyType(OneExecutionStateStrategy::class)
fun MvpView.showAlert(message: String): AlertDialog {
    val view = (this as MvpViewState<*>).views.find { it is Context || it is Fragment }

    return when (view) {
        is Context -> view.alert(message)
        is Fragment -> view.context!!.alert(message)
        else -> null!!
    }.also {
        it.positiveButton(android.R.string.ok)
    }.show()
}

@StateStrategyType(OneExecutionStateStrategy::class)
fun MvpView.showMessage(message: String) {
    val view = (this as MvpViewState<*>).views.find { it is Context || it is Fragment }

    when (view) {
        is Context -> view.toast(message)
        is Fragment -> view.toast(message)
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
fun MvpView.showLongMessage(message: String) {
    val view = (this as MvpViewState<*>).views.find { it is Context || it is Fragment }

    when (view) {
        is Context -> view.longToast(message)
        is Fragment -> view.longToast(message)
    }
}

@StateStrategyType(OneExecutionStateStrategy::class)
fun MvpView.showMessage(message: Int) {
    val view = (this as MvpViewState<*>).views.find { it is Context || it is Fragment }

    when (view) {
        is Context -> view.toast(message)
        is Fragment -> view.toast(message)
    }
}