package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.EventTypeIconFinder
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.utils.extensions.getDrawableCompat


class ArchiveEventsRecyclerAdapter(private val onClick: (View, EventCardModel) -> Unit)
    : GenericRecyclerAdapter<EventCardModel, ArchiveEventsRecyclerAdapter.ViewHolder>(EventCardModelDifferCallback) {

    override fun getItemId(item: EventCardModel): Long {
        return item.hid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_event_horizontal, parent, false)
        return ViewHolder(view, onClick)
    }


    class ViewHolder(private val view: View, private val callback: (View, EventCardModel) -> Unit)
        : BindableViewHolder<EventCardModel>(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val dateAndType by lazy { view.findViewById<TextView>(R.id.event_card_type_and_date) }
        private val icon by lazy { view.findViewById<ImageView>(R.id.event_card_icon) }

        override fun bind(model: EventCardModel) {
            val iconId = EventTypeIconFinder.findIconByEventType(model.typeTitle)
            icon.setImageDrawable(view.context.getDrawableCompat(iconId))
            title.text = model.title
            dateAndType.text = "%s/%s".format(model.typeTitle, model.date)
            view.setOnClickListener {
                callback(view, model)
            }
        }
    }
}

