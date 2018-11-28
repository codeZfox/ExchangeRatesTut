package com.codezfox.exchangeratesmvp.di

import com.codezfox.exchangeratesmvp.data.server.ApiProvider
import com.codezfox.exchangeratesmvp.data.server.FinanceApi
import com.codezfox.exchangeratesmvp.domain.CurrencyRatesRepository
import com.codezfox.exchangeratesmvp.data.repositories.currencyrates.CurrencyRatesRepositoryImpl
import com.google.gson.*
import dagger.Module
import dagger.Provides
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton


@Module
class ServerModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideApi(gson: Gson): FinanceApi = ApiProvider(baseUrl, gson).get()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
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

    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(api: FinanceApi, gson: Gson): CurrencyRatesRepository = CurrencyRatesRepositoryImpl(api, gson)


}