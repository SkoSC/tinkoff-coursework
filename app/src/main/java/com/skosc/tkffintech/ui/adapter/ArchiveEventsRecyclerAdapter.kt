package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.EventCardModel


class ArchiveEventsRecyclerAdapter(private val onClick: (EventCardModel) -> Unit) : RecyclerView.Adapter<ArchiveEventsRecyclerAdapter.ViewHolder>() {
    var items: List<EventCardModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_event_horizontal, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model, onClick)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val dateAndType by lazy { view.findViewById<TextView>(R.id.event_card_type_and_date) }

        fun bind(model: EventCardModel, callback: (EventCardModel) -> Unit) {
            title.text = model.title
            dateAndType.text = "%s/%s".format(model.typeTitle, model.date)
            view.setOnClickListener {
                callback(model)
            }
        }
    }
}