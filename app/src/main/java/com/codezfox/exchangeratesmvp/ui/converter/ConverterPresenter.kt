package com.codezfox.exchangeratesmvp.ui.converter

import com.arellomobile.mvp.InjectViewState
import com.codezfox.exchangeratesmvp.data.models.BestRate
import com.codezfox.exchangeratesmvp.data.models.BestRateCurrency
import com.codezfox.exchangeratesmvp.data.models.Currency
import com.codezfox.exchangeratesmvp.ui._base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

@InjectViewState
class ConverterPresenter(
        private val interactor: ConverterInteractor,
        private val router: Router
) : BasePresenter<ConverterView>() {

    private var rateCurrency: List<BestRateCurrency> = emptyList()
    private var currency: BestRateCurrency = BestRateCurrency(BestRate("BYN", 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, null, 1.0, null, 0.0, "BYN"),
            Currency("BYN", "Белорусский рубль", "белорусский рубль", "рубль", "BYN", "", "http://img.tyt.by/i/icons/android/new/BYN.png", "1", "1", 4))
//    private lateinit var rateUsd: BestRate

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        disposable.add(interactor.loadRates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    this.rateCurrency = listOf(currency, *it.first.toTypedArray())
//                    this.currency = this.rateCurrency.first()
//                    rateUsd = this.rateCurrency.find { it.currency.code == "USD" }!!.rate

                    input("")
                }, {
                    it.printStackTrace()
                }))

    }

    fun input(text: String) {
        val fractionalPart = text.substringAfter(".")
        if (text.contains(".") && fractionalPart.length > 4) {
            return
        }
        val input = text.replace(" ", "").toDoubleOrNull() ?: 0.0
        val list = rateCurrency.map {
            val value = input * currency.rate.nb / it.rate.nb * it.currency.amount.toDouble() / currency.currency.amount.toDouble()
            var sa = Currency.rateForUI(value, it.currency.scale, false)

            if (currency == it && text.contains('.')) {
                val lastOrNull = text.lastOrNull()
                if (lastOrNull == '.') {
                    sa += lastOrNull
                }
                val fractionalPart = text.substringAfter(".")
                if (fractionalPart.lastOrNull() == '0') {
                    sa += (if (sa.contains('.')) "" else ".") + fractionalPart.substringAfter(sa.substringAfter("."))
                }
            }

            ConverterRate(it, sa, it.currency == currency.currency)
        }
        viewState.show(list)

    }

    fun selectCurrency(converterRate: ConverterRate) {
        this.currency = converterRate.bestRateCurrency
        input(converterRate.summary)
    }

    fun onBackPressed() {
        router.exit()
    }
}