package com.codezfox.exchangeratesmvp.extensions

import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic


inline fun <reified T : Any> KodeinAware.get(tag: Any? = null) = direct.Instance<T>(generic(), tag)
