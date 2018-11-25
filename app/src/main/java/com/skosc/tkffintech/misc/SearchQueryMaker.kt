package com.skosc.tkffintech.misc

/**
 * Interface for making search query for different languages based on passed [Mode] parameter,
 */
interface SearchQueryMaker {
    /**
     * Describes how precise search should be
     */
    enum class Mode { Exact, PARTIAL, SOFT }

    /**
     * Create search query form strings and mode flag
     */
    fun from(userInput: String, mode: Mode): String
}

/**
 * [SQLSearchQueryMaker] for SQL's LIKE statement
 */
class SQLSearchQueryMaker : SearchQueryMaker {
    private companion object {
        private const val MASK_ANY = "%" // SQL LIKE mask matches 0..n symbols
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