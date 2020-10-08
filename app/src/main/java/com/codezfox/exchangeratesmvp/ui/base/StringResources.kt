package com.codezfox.exchangeratesmvp.ui.base

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize


sealed class StringResources : Parcelable {
    companion object {
        private val empty = "".create()
        fun empty() = empty

        fun create(string: String) = HardString(string)

        fun createById(stringIdName: String) = ContextLazyString(stringIdName)
        fun createById(stringIdName: String, vararg params: String) = ContextLazyString(stringIdName, *params)
        fun createById(stringIdName: String, vararg params: StringResources) = ContextLazyString(stringIdName, *params)

        fun create(@StringRes @IdRes string: Int) = ContextString(string)
        fun create(@StringRes @IdRes string: Int, vararg params: String) = ContextString(string, *params)
        fun create(@StringRes @IdRes string: Int, vararg params: StringResources) = ContextString(string, *params)
        fun create(any: Any) = when (any) {
            is Int -> ContextString(any)
            is String -> HardString(any)
            is StringResources -> any
            else -> HardString("Error: ${any.javaClass.simpleName}")
        }

        fun createFormat(format: String, vararg params: StringResources) = FormatString(format, *params)
    }
}

@Parcelize
data class HardString(val string: String) : StringResources(), Parcelable

@Parcelize
class ContextString(@StringRes val string: Int, vararg val params: StringResources) : StringResources(), Parcelable {
    constructor(string: Int, vararg params: String) : this(string, *params.map { it.create() }.toTypedArray())
    constructor(string: Int) : this(string, *arrayOf<StringResources>())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContextString

        if (string != other.string) return false
        if (!params.contentEquals(other.params)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string
        result = 31 * result + params.contentHashCode()
        return result
    }

}

@Parcelize
class ContextLazyString(val resourceName: String, vararg val params: StringResources) : StringResources(), Parcelable {
    constructor(stringIdName: String, vararg params: String) : this(stringIdName, *params.map { it.create() }.toTypedArray())
    constructor(stringIdName: String) : this(stringIdName, *arrayOf<StringResources>())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContextLazyString

        if (resourceName != other.resourceName) return false
        if (!params.contentEquals(other.params)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = resourceName.hashCode()
        result = 31 * result + params.contentHashCode()
        return result
    }
}

@Parcelize
class ContextQuantityString(
    @PluralsRes val string: Int, val quantity: Int,
    vararg val params: Int
) : StringResources(), Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContextQuantityString

        if (string != other.string) return false
        if (quantity != other.quantity) return false
        if (!params.contentEquals(other.params)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string
        result = 31 * result + quantity
        result = 31 * result + params.contentHashCode()
        return result
    }
}

@Parcelize
class FormatString(val format: String, vararg val params: StringResources) : StringResources(), Parcelable {

    constructor(format: String, vararg params: String) : this(format, *params.map { it.create() }.toTypedArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormatString

        if (format != other.format) return false
        if (!params.contentEquals(other.params)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = format.hashCode()
        result = 31 * result + params.contentHashCode()
        return result
    }
}

fun StringResources?.orEmpty() = this ?: StringResources.empty()
fun String.create() = HardString(this)