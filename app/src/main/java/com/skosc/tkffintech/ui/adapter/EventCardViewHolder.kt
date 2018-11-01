package com.skosc.tkffintech.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.ui.model.EventCardModel

abstract class EventCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: EventCardModel)
}