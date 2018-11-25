package com.skosc.tkffintech.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.utils.extensions.getColorCompat


class OnGoingEventsRecyclerAdapter(private val onClickCallback: (View, EventCardModel) -> Unit)
    : GenericRecyclerAdapter<EventCardModel, OnGoingEventsRecyclerAdapter.ViewHolder>(EventCardModelDifferCallback) {

    override fun getItemId(item: EventCardModel): Long {
        return item.hid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_event_vertical, parent, false)
        return ViewHolder(view, onClickCallback)
    }

    class ViewHolder(
            private val view: View,
            private val callback: (View, EventCardModel) -> Unit
    ) : BindableViewHolder<EventCardModel>(view) {

        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val date by lazy { view.findViewById<TextView>(R.id.event_card_date) }
        private val typeTitle by lazy { view.findViewById<TextView>(R.id.event_card_type_title) }

        override fun bind(model: EventCardModel) {
            title.text = model.title
            date.text = model.date
            if (model.typeTitle.isEmpty()) {
                typeTitle.text = view.resources.getString(R.string.event_type_unknown)
            } else {
                typeTitle.text = model.typeTitle
            }
            val color = view.context.getColorCompat(model.typeColor)
            val colorStateList = ColorStateList.valueOf(color)
            typeTitle.backgroundTintList = colorStateList
            view.setOnClickListener {
                callback(view, model)
            }
        }
    }
}