package com.skosc.tkffintech.ui.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.skosc.tkffintech.entities.User

class UsersSpinnerAdapter(ctx: Context, val items: List<User>)
    : ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, items.map { it.name }) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}