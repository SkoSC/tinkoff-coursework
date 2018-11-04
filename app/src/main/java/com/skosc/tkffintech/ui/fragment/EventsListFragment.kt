package com.skosc.tkffintech.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.EventsListRecyclerAdapter
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.viewmodel.events.EventsViewModel
import com.skosc.tkffintech.viewmodel.events.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_events_list.*
import java.lang.IllegalArgumentException

class EventsListFragment : TKFFragment() {
    companion object {
        const val ON_GOING = 0
        const val ARCHIVE = 1
    }

    private var recyclerMode = ON_GOING

    private val vm by lazy { getViewModel(EventsViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        recyclerMode = arguments?.getInt("mode") ?: EventsListRecyclerAdapter.MODE_LARGE
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        events_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_recycler.adapter = EventsListRecyclerAdapter(recyclerMode)

        val events = when(recyclerMode) {
            ON_GOING -> vm.onGoingEvents
            ARCHIVE -> vm.archiveEvents
            else -> throw IllegalArgumentException("Unsupported mode")
        }
        events.observe(this, Observer { eventsInfo ->
            val adapter = events_recycler.adapter as EventsListRecyclerAdapter
            adapter.items = eventsInfo
                    .map { EventCardModel.from(context!!, it) }
            adapter.notifyDataSetChanged()
            events_refresh.isRefreshing = false
        })

        events_refresh.setOnRefreshListener {
            vm.update()
        }
    }
}
