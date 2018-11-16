package com.skosc.tkffintech.ui.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.skosc.tkffintech.utils.ItemSorter
import com.skosc.tkffintech.viewmodel.UserWithGradesSum

class SortSpinnerAdapter(ctx: Context, val items: List<ItemSorter<UserWithGradesSum>>)
    : ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, items.map { ctx.getString(it.name) }) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}