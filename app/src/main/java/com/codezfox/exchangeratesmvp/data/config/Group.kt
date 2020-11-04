package com.codezfox.exchangeratesmvp.data.config

sealed class Group(val list: List<Config<*>>) {

    fun mapValues(): Map<String, *> = list.map { it.key to it.value }.toMap()

    fun mapDefaultValues(): Map<String, *> = list.map { it.key to it.default }.toMap()

    class BestRates(
            var isBestRateDiff: Config.BooleanConfig = Config.BooleanConfig(BOOLEAN_BEST_RATE_DIFF),
            var isBestRateDiffNb: Config.BooleanConfig = Config.BooleanConfig(BOOLEAN_BEST_RATE_DIFF_NB),
            var isVisibleBCSE: Config.BooleanConfig = Config.BooleanConfig(BOOLEAN_BEST_RATE_BCSE),
            var isBestRateDiffBCSE: Config.BooleanConfig = Config.BooleanConfig(BOOLEAN_BEST_RATE_DIFF_BCSE),
            var isVisibleNbDate: Config.BooleanConfig = Config.BooleanConfig(BOOLEAN_BEST_RATE_NB_DATE)
    ) : Group(listOf(isBestRateDiff, isBestRateDiffNb, isVisibleBCSE, isBestRateDiffBCSE, isVisibleNbDate)) {

        companion object {
            internal const val BOOLEAN_BEST_RATE_DIFF = "best_rate_diff"
            internal const val BOOLEAN_BEST_RATE_DIFF_NB = "best_rate_diff_nb"
            internal const val BOOLEAN_BEST_RATE_BCSE = "best_rate_bcse"
            internal const val BOOLEAN_BEST_RATE_DIFF_BCSE = "best_rate_diff_bcse"
            internal const val BOOLEAN_BEST_RATE_NB_DATE = "best_rate_nb_date"
        }
    }
}