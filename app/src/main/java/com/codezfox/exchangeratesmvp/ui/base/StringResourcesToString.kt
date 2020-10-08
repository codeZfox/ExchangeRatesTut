package com.codezfox.exchangeratesmvp.ui.base

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView

fun TextView.setText(stringResources: StringResources) {
    this.text = this.getString(stringResources)
}

fun Context.getString(stringResources: StringResources): String {
    return when (stringResources) {
        is HardString -> stringResources.string
        is ContextString -> {
            if (stringResources.params.isNotEmpty()) {
                getString(stringResources.string, *stringResources.params.map { getString(it) }.toTypedArray())
            } else {
                getString(stringResources.string)
            }.takeUnless { it == "false" }
                ?: getString(ContextLazyString(resources.getResourceEntryName(stringResources.string), *stringResources.params))

        }
        is ContextLazyString -> {
            try {
                val identifier = resources.getIdentifier(stringResources.resourceName, "string", packageName)
                getString(ContextString(identifier, *stringResources.params))
            } catch (e: Exception) {
                stringResources.resourceName
            }
        }
        is ContextQuantityString ->
            if (stringResources.params.isNotEmpty()) {
                resources.getQuantityString(
                    stringResources.string,
                    stringResources.quantity,
                    *stringResources.params.toTypedArray()
                )
            } else {
                resources.getQuantityString(
                    stringResources.string,
                    stringResources.quantity
                )
            }
        is FormatString -> String.format(stringResources.format, *stringResources.params.map { getString(it) }.toTypedArray())
    }
}


fun View.getString(stringResources: StringResources): String = context.getString(stringResources)

fun Fragment.getString(stringResources: StringResources): String =
    context?.getString(stringResources) ?: ""
