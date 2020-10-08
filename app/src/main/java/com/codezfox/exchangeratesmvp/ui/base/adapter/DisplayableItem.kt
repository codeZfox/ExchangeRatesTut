package com.codezfox.exchangeratesmvp.ui.base.adapter

interface DisplayableItem {

    fun areItemsTheSame(): String

    override fun equals(other: Any?): Boolean

}