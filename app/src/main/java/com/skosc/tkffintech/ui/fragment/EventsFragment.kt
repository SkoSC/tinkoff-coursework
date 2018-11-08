package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.ArchiveEventsRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.OnGoingEventsRecyclerAdapter
import com.skosc.tkffintech.ui.fragment.EventsListFragment.Companion.ARCHIVE
import com.skosc.tkffintech.ui.fragment.EventsListFragment.Companion.ON_GOING
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.viewmodel.events.EventsListViewModel
import com.skosc.tkffintech.viewmodel.events.EventsListViewModelArchive
import com.skosc.tkffintech.viewmodel.events.EventsListViewModelOngoing
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : TKFFragment() {

    private val onGoingVm by lazy { getViewModel(EventsListViewModelOngoing::class) }
    private val archiveVm by lazy { getViewModel(EventsListViewModelArchive::class) }
    private val navController by lazy { findNavController(events_ongoing_more) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupOnGoingEventsRecycler()
        setupArchiveEventsRecycler()
        events_refresh.setOnRefreshListener {
            onGoingVm.update()
            archiveVm.update()
        }
    }

    private fun setupArchiveEventsRecycler() {
        archiveVm.events.observe(this, Observer { eventsInfo ->
            // TODO Move refresher hiding from hire
            events_refresh.isRefreshing = false
            val cards = eventsInfo.map { EventCardModel.from(context!!, it) }
            val adapter = events_archive_recycler.adapter as ArchiveEventsRecyclerAdapter
            adapter.items = cards
            adapter.notifyDataSetChanged()

        })

        events_archive_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_archive_recycler.adapter = ArchiveEventsRecyclerAdapter {
            navController.navigate(R.id.action_navigation_event_detail, bundleOf(
                    EventDetailFragment.ARG_MODEL to it.hid
            ))
        }

        events_archive_more.setOnClickListener {
            navController.navigate(
                    R.id.action_navigation_events_more,
                    bundleOf(EventsListFragment.ARG_MODE to ARCHIVE)
            )
        }
    }

    private fun setupOnGoingEventsRecycler() {
        onGoingVm.events.observe(this, Observer { eventsInfo ->
            // TODO Move refresher hiding from hire
            events_refresh.isRefreshing = false
            val cards = eventsInfo.map { EventCardModel.from(context!!, it) }
            val adapter = events_actual_recycler.adapter as OnGoingEventsRecyclerAdapter
            adapter.items = cards
            adapter.notifyDataSetChanged()

        })

        events_actual_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        events_actual_recycler.adapter = OnGoingEventsRecyclerAdapter { v, model ->
            val extras = FragmentNavigator.Extras.Builder()
                    .addSharedElement(v.findViewById(R.id.event_card_image), "event_card_image_shared")
                    .build()

            navController.navigate(R.id.action_navigation_event_detail, bundleOf(
                    EventDetailFragment.ARG_MODEL to model.hid
            ), null, extras)
        }

        events_ongoing_more.setOnClickListener {
            navController.navigate(
                    R.id.action_navigation_events_more,
                    bundleOf(EventsListFragment.ARG_MODE to ON_GOING)
            )
        }
    }
}
