package com.skosc.tkffintech.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.ui.adapter.EventsListRecyclerAdapter
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.viewmodel.events.EventsViewModel
import kotlinx.android.synthetic.main.fragment_events_list.*
import java.lang.IllegalArgumentException

class EventsListFragment : TKFFragment() {
    companion object {
        const val ON_GOING = 0
        const val ARCHIVE = 1
        const val ARG_MODE = "mode"
    }

    private val navController by lazy { Navigation.findNavController(events_refresh) }
    private val vm by lazy { getViewModel(EventsViewModel::class) }

    private var recyclerMode = ON_GOING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        recyclerMode = arguments?.getInt(ARG_MODE) ?: EventsListRecyclerAdapter.MODE_LARGE

        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupEventsRecycler()
        setupRefreshView()
    }

    private fun setupRefreshView() {
        events_refresh.setOnRefreshListener {
            vm.update()
        }

        getEvents().observe(this, Observer {
            events_refresh.isRefreshing = false
        })
    }

    private fun setupEventsRecycler() {
        events_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_recycler.adapter = EventsListRecyclerAdapter(recyclerMode) {
            navController.navigate(R.id.action_navigation_event_detail, bundleOf(
                    EventDetailFragment.ARG_MODEL to it
            ))
        }

        getEvents().observe(this, Observer { eventsInfo ->
            val adapter = events_recycler.adapter as EventsListRecyclerAdapter
            adapter.items = eventsInfo
                    .map { EventCardModel.from(context!!, it) }
            adapter.notifyDataSetChanged()
        })
    }

    private fun getEvents(): LiveData<List<EventInfo>> {
        val events = when (recyclerMode) {
            ON_GOING -> vm.onGoingEvents
            ARCHIVE -> vm.archiveEvents
            else -> throw IllegalArgumentException("Unsupported mode")
        }
        return events
    }
}
