package com.skosc.tkffintech.utils

interface SearchQueryMaker {
    enum class Mode { Exact, PARTIAL, SOFT }

    fun from(userInput: String, mode: Mode): String
}

class SQLSearchQueryMaker : SearchQueryMaker {
    private companion object {
        private const val MASK_ANY = "%"
    }

    override fun from(userInput: String, mode: SearchQueryMaker.Mode): String {
        return when (mode) {
            SearchQueryMaker.Mode.Exact -> userInput
            SearchQueryMaker.Mode.PARTIAL -> "$MASK_ANY$userInput$MASK_ANY"
            SearchQueryMaker.Mode.SOFT -> userInput.split("").joinToString(
                    separator = MASK_ANY,
                    prefix = MASK_ANY,
                    postfix = MASK_ANY
            )
        }
    }
}