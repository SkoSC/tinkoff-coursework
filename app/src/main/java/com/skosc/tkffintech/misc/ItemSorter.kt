package com.skosc.tkffintech.misc

import androidx.annotation.StringRes

/**
 * Binds UI representation and sorting algorithm. &nbsp;
 *
 * Name with value = 0 is considered empty by convention
 */
class ItemSorter<T>(
        @StringRes val name: Int = 0,
        val comparator: Comparator<T>
)