package com.codezfox.exchangeratesmvp.di

import com.codezfox.exchangeratesmvp.BuildConfig
import com.codezfox.exchangeratesmvp.data.network.ApiProvider
import com.codezfox.exchangeratesmvp.data.network.FinanceApi
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepositoryImpl
import com.google.gson.*
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val serverModule = Kodein.Module("serverModule") {

    bind<FinanceApi>() with factory { baseUrl: String, gson: Gson -> ApiProvider(baseUrl, gson).get() }

    bind<Gson>() with provider {
        GsonBuilder()
                .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {

                    val df = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

                    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
                        return try {
                            df.parse(json?.getAsString())
                        } catch (e: ParseException) {
                            null
                        }

                    }
                }).create()
    }

    bind<CurrencyRatesRepository>() with singleton { CurrencyRatesRepositoryImpl(instance(arg = M(BuildConfig.ORIGIN_ENDPOINT, instance<Gson>())), instance()) }
}
