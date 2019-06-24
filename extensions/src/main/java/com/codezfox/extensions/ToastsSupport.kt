@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.codezfox.extensions

import android.support.v4.app.Fragment
import android.widget.Toast
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast


/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.toast(message: Int): Unit? = activity?.toast(message)


/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.toast(message: CharSequence): Unit? = activity?.toast(message)


/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.longToast(message: Int): Unit? = activity?.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.longToast(message: CharSequence): Unit? = activity?.longToast(message)
