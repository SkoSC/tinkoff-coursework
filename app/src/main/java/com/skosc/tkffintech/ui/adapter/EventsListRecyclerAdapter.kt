package com.skosc.tkffintech.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.EventCardModel
import java.lang.IllegalArgumentException

class EventsListRecyclerAdapter(private val mode: Int) : RecyclerView.Adapter<EventCardViewHolder>() {
    companion object {
        const val MODE_LARGE: Int = 0
        const val MODE_SMALL: Int = 1
    }

    var items: List<EventCardModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCardViewHolder {
        return when(viewType) {
            MODE_LARGE -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_event_large, parent, false)
                LargeViewHolder(view)
            }
            MODE_SMALL -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_event_small, parent, false)
                SmallViewHolder(view)
            }
            else -> throw IllegalArgumentException("Card Type with id $viewType not supported")
        }
    }

    override fun onBindViewHolder(holder: EventCardViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    override fun getItemViewType(position: Int): Int = mode

    override fun getItemCount(): Int = items.size

    class LargeViewHolder(private val view: View) : EventCardViewHolder(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val date by lazy { view.findViewById<TextView>(R.id.event_card_date) }
        private val typeTitle by lazy { view.findViewById<TextView>(R.id.event_card_type_title) }
        private val description by lazy { view.findViewById<TextView>(R.id.event_card_description) }

        override fun bind(model: EventCardModel) {
            title.text = model.title
            date.text = model.date
            typeTitle.text = model.typeTitle
            description.text = model.description
        }
    }

    class SmallViewHolder(private val view: View) : EventCardViewHolder(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val dateAndType by lazy { view.findViewById<TextView>(R.id.event_card_type_and_date) }

        override fun bind(model: EventCardModel) {
            Log.i("TKF_INFO", model.title)
            title.text = model.title
            dateAndType.text = "%s/%s".format(model.typeTitle, model.date)
        }
    }
}