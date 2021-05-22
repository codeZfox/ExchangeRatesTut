package com.codezfox.exchangeratesmvp.data.models

import androidx.annotation.DrawableRes
import com.codezfox.exchangeratesmvp.R

enum class CurrencyType(val pluralShort: String, @DrawableRes val flag:Int? = null) {
    BYN("рублей", R.drawable.flag_byn),
    USD("доллар", R.drawable.flag_usa),
    EUR("евро", R.drawable.flag_euro),
    RUB("рублей", R.drawable.flag_rus),
    UAH("гривен", R.drawable.flag_uah),
    PLN("злотых", R.drawable.flag_poland),
    GBP("фунт", R.drawable.flag_united_kingdom),
    CHF("франк", R.drawable.flag_switzerland),
    JPY("йен", R.drawable.flag_japan),
    KZT("тенге", R.drawable.flag_kazakhstan),
    CZK("крон", R.drawable.flag_czech),
    BGN("лев", R.drawable.flag_bulgaria),
    TRY("лир", R.drawable.flag_turkey),
    AUD("доллар", R.drawable.flag_australia),
    NOK("крон", R.drawable.flag_norway),
    SEK("крон", R.drawable.flag_sweden),
    CNY("юаней", R.drawable.flag_china),
    DKK("крон", R.drawable.flag_denmark),
    CAD("доллар", R.drawable.flag_canada);

    companion object {

        fun valueOfOrNull(string: String): CurrencyType? {
            return try {
                valueOf(string)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
