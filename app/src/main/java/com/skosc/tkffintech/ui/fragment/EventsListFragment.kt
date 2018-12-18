package com.skosc.tkffintech.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.EventsListRecyclerAdapter
import com.skosc.tkffintech.ui.contracts.SearchViewProvider
import com.skosc.tkffintech.ui.model.toAdapterModels
import com.skosc.tkffintech.utils.extensions.observe
import com.skosc.tkffintech.viewmodel.events.ArchiveEventsListViewModel
import com.skosc.tkffintech.viewmodel.events.OngoingEventsListViewModel
import kotlinx.android.synthetic.main.fragment_events_list.*

class EventsListFragment : TKFFragment() {
    companion object {
        const val ON_GOING = 0
        const val ARCHIVE = 1
        const val ARG_MODE = "mode"
    }

    private val vm by lazy {
        val cls = when (recyclerMode) {
            ON_GOING -> OngoingEventsListViewModel::class
            ARCHIVE -> ArchiveEventsListViewModel::class
            else -> throw IllegalArgumentException("Unsupported mode: $recyclerMode")
        }
        getViewModel(cls)
    }

    private val navController by lazy { Navigation.findNavController(events_refresh) }
    private val searchView: SearchView by lazy { (activity as SearchViewProvider).searchView }

    private var recyclerMode = ON_GOING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        recyclerMode = arguments?.getInt(ARG_MODE) ?: EventsListRecyclerAdapter.MODE_LARGE
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupSearch() // Requires activity attached
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventsRecycler()
        setupRefreshView()
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vm.searchEvents(newText ?: "")
                return true
            }
        })
    }

    private fun setupRefreshView() {
        events_refresh.setOnRefreshListener {
            vm.forceUpdate()
        }

        vm.events.observe(this) {
            events_refresh.isRefreshing = false
        }

        vm.hideSearcheView.observe(this) {
            collapseSearch()
        }
    }

    private fun setupEventsRecycler() {
        events_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_recycler.adapter = EventsListRecyclerAdapter(recyclerMode) {
            navController.navigate(R.id.action_navigation_event_detail, bundleOf(
                    EventDetailFragment.ARG_MODEL to it.hid
            ))
        }

        vm.events.observe(this, Observer { eventsInfo ->
            val adapter = events_recycler.adapter as EventsListRecyclerAdapter
            val items = eventsInfo.toAdapterModels(context!!)
            adapter.submitItems(items)
            adapter.notifyDataSetChanged()
        })
    }

    private fun collapseSearch() {
        (activity as SearchViewProvider).dismissSearchView()
    }
}
