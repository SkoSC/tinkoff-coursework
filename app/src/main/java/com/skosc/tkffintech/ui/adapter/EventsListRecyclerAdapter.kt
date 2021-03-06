package com.skosc.tkffintech.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.resolver.EventTypeIconFinder
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.utils.extensions.getColorCompat
import com.skosc.tkffintech.utils.extensions.getDrawableCompat

class EventsListRecyclerAdapter(private val mode: Int, private val onClick: (EventCardModel) -> Unit)
    : RecyclerView.Adapter<EventCardViewHolder>() {
    companion object {
        const val MODE_LARGE: Int = 0
        const val MODE_SMALL: Int = 1
    }

    private val differ = AsyncListDiffer(this, GenericDiffUtilCallback<EventCardModel>())

    init {
        setHasStableIds(true)
    }

    fun submitItems(items: List<EventCardModel>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCardViewHolder {
        return when (viewType) {
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
            else -> throw IllegalArgumentException("Card Type with homeworkId $viewType not supported")
        }
    }

    override fun onBindViewHolder(holder: EventCardViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model, onClick)
    }

    override fun getItemViewType(position: Int): Int = mode

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemId(position: Int): Long = differ.currentList[position].hid

    class LargeViewHolder(private val view: View) : EventCardViewHolder(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val date by lazy { view.findViewById<TextView>(R.id.event_card_date) }
        private val typeTitle by lazy { view.findViewById<TextView>(R.id.event_card_type_title) }
        private val description by lazy { view.findViewById<TextView>(R.id.event_card_description) }

        override fun bind(model: EventCardModel, onClick: (EventCardModel) -> Unit) {
            super.bind(model, onClick)
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
            description.text = model.description
        }
    }

    class SmallViewHolder(private val view: View) : EventCardViewHolder(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.event_card_title) }
        private val dateAndType by lazy { view.findViewById<TextView>(R.id.event_card_type_and_date) }
        private val icon by lazy { view.findViewById<ImageView>(R.id.event_card_icon) }

        override fun bind(model: EventCardModel, onClick: (EventCardModel) -> Unit) {
            super.bind(model, onClick)
            val iconId = EventTypeIconFinder.findIconByEventType(model.typeTitle)
            icon.setImageDrawable(view.context.getDrawableCompat(iconId))
            title.text = model.title
            dateAndType.text = "%s/%s".format(model.typeTitle, model.date)
        }
    }
}