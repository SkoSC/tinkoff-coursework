package com.skosc.tkffintech.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.skosc.tkffintech.ui.model.EventCardModel

object EventCardModelDifferCallback : DiffUtil.ItemCallback<EventCardModel>() {
    override fun areItemsTheSame(oldItem: EventCardModel, newItem: EventCardModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: EventCardModel, newItem: EventCardModel): Boolean {
        return oldItem == newItem
    }
}