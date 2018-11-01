package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.ArchiveEventsRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.OnGoingEventsRecyclerAdapter
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.viewmodel.events.EventsViewModel
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : TKFFragment() {

    val vm by lazy { getViewModel(EventsViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onStart() {
        super.onStart()
        vm.onGoingEvents.observe(this, Observer { eventsInfo ->
            val cards = eventsInfo.map { EventCardModel.from(context!!, it) }
            val adapter = events_actual_recycler.adapter as OnGoingEventsRecyclerAdapter
            adapter.items = cards
            adapter.notifyDataSetChanged()

        })
        events_actual_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        events_actual_recycler.adapter = OnGoingEventsRecyclerAdapter()

        vm.archiveEvents.observe(this, Observer { eventsInfo ->
            val cards = eventsInfo.map { EventCardModel.from(context!!, it) }
            val adapter = events_archive_recycler.adapter as ArchiveEventsRecyclerAdapter
            adapter.items = cards
            adapter.notifyDataSetChanged()

        })
        events_archive_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_archive_recycler.adapter = ArchiveEventsRecyclerAdapter()
    }
}
