package com.skosc.tkffintech.utils

import androidx.annotation.StringRes

class ItemSorter<T>(
        @StringRes val name: Int,
        val comparator: Comparator<T>
)