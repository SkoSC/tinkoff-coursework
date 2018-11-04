package com.skosc.tkffintech.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.ui.model.EventCardModel

abstract class EventCardViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(model: EventCardModel, onClick: (EventCardModel) -> Unit) {
        view.setOnClickListener {
            onClick(model)
        }
    }
}