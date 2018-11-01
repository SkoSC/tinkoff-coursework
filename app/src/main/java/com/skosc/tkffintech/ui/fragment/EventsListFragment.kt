package com.skosc.tkffintech.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.EventCardViewHolder
import com.skosc.tkffintech.ui.adapter.EventsListRecyclerAdapter
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.viewmodel.events.EventsViewModel
import kotlinx.android.synthetic.main.fragment_events_list.*

class EventsListFragment : TKFFragment() {
    private val vm by lazy { getViewModel(EventsViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        events_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_recycler.adapter = EventsListRecyclerAdapter(EventsListRecyclerAdapter.MODE_LARGE)
        vm.onGoingEvents.observe(this, Observer { eventsInfo ->
            val adapter = events_recycler.adapter as EventsListRecyclerAdapter
            adapter.items = eventsInfo.map { EventCardModel.from(context!!, it) }
            adapter.notifyDataSetChanged()
        })
    }
}
