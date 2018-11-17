package com.skosc.tkffintech.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<Model>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: Model)
}