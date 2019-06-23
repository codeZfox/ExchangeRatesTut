package com.codezfox.exchangeratesmvp.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.ConnectivityManager

/**
 * Convert dp to pixel
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert dp to pixel
 */
val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)


fun Context.getDisplayWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getDisplayHeight(): Int {
    return resources.displayMetrics.heightPixels
}


fun Context.isNetworkConnected() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null

fun Context.isIntentAvailable(action: String): Boolean {
    return this.packageManager.queryIntentActivities(Intent(action), PackageManager.MATCH_DEFAULT_ONLY).size > 0
}
