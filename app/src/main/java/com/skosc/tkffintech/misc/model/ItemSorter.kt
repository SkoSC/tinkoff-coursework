package com.skosc.tkffintech.misc.model

import androidx.annotation.StringRes

/**
 * Binds UI representation and sorting algorithm. &nbsp;
 *
 * Name with value = 0 is considered empty by convention
 */
class ItemSorter<T>(
        /**
         * Human readable name of sorter
         */
        @StringRes val name: Int = 0,

        /**
         * Actual comparing algorithm to be used in sorter
         */
        val comparator: Comparator<T>
)