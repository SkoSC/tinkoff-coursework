package com.skosc.tkffintech.ui.fragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.EventInfo
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.ui.adapter.ArchiveEventsRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.GenericRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.OnGoingEventsRecyclerAdapter
import com.skosc.tkffintech.ui.fragment.EventsListFragment.Companion.ARCHIVE
import com.skosc.tkffintech.ui.fragment.EventsListFragment.Companion.ON_GOING
import com.skosc.tkffintech.ui.model.EventCardModel
import com.skosc.tkffintech.ui.model.toAdapterModels
import com.skosc.tkffintech.utils.extensions.observe
import com.skosc.tkffintech.viewmodel.events.ArchiveEventsListViewModel
import com.skosc.tkffintech.viewmodel.events.EventsListViewModel
import com.skosc.tkffintech.viewmodel.events.OngoingEventsListViewModel
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : TKFFragment() {
    companion object {
        private const val MAX_RECYCLER_ITEMS = 10
    }

    private val onGoingVm by lazy { getViewModel(OngoingEventsListViewModel::class) }
    private val archiveVm by lazy { getViewModel(ArchiveEventsListViewModel::class) }

    private val navController by lazy { findNavController(events_ongoing_more) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupCardExpansion(onGoingVm, events_ongoing_recycler, events_ongoing_dropdown)
        setupCardExpansion(archiveVm, events_archive_recycler, events_archive_dropdown)

        setupOnGoingEventsRecycler()
        setupArchiveEventsRecycler()
        setupRefresh()

    }

    override fun onResume() {
        super.onResume()
        listOf(onGoingVm, archiveVm).forEach { vm ->
            vm.checkForUpdates().observe(this, this::handleUpdate)
        }
    }

    private fun setupCardExpansion(vm: EventsListViewModel, recycler: RecyclerView, button: View) {
        vm.cardExpanded.observe(this, Observer { expanded ->
            recycler.visibility = if (expanded) View.VISIBLE else View.GONE
            val rotation = if (expanded) 180F else 0F // if expanded - up, if collapsed - down
            button.animate().rotation(rotation)
        })

        button.setOnClickListener {
            // IMPORTANT: Card expands if vm.cardExpanded.value == null, by design
            if (vm.cardExpanded.value == true) {
                vm.collapseCard()
            } else {
                vm.expandCard()
            }
        }
    }

    private fun setupRefresh() {
        events_refresh.setOnRefreshListener {
            listOf(onGoingVm.forceUpdate(), archiveVm.forceUpdate()).map {
                it.observe(this, this::handleUpdate)
            }
        }
    }

    private fun setupArchiveEventsRecycler() {
        events_archive_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        events_archive_recycler.adapter = ArchiveEventsRecyclerAdapter(this::onEventClickedListener)

        events_archive_more.setOnClickListener(onMoreEventsClickedClosure(ARCHIVE))
        bindRecyclerToData(events_archive_recycler, events_loading_spinner_archive, archiveVm.events)
    }

    private fun setupOnGoingEventsRecycler() {
        events_ongoing_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        events_ongoing_recycler.adapter = OnGoingEventsRecyclerAdapter(this::onEventClickedListener)

        events_ongoing_more.setOnClickListener(onMoreEventsClickedClosure(ON_GOING))
        bindRecyclerToData(events_ongoing_recycler, events_loading_spinner_ongoing, onGoingVm.events)
    }

    /**
     * Binds data to recycler adapter assuming, that recycler.adapter is [GenericRecyclerAdapter]
     */
    private fun bindRecyclerToData(recycler: RecyclerView, progressBar: ProgressBar, data: LiveData<List<EventInfo>>) {
        data.observe(this, Observer { eventsInfo ->
            setLoadingSpinnerVisibility(progressBar, eventsInfo)

            val cards = eventsInfo.toAdapterModels(context!!)
                    .take(MAX_RECYCLER_ITEMS)

            @Suppress("UNCHECKED_CAST")
            val adapter = recycler.adapter as GenericRecyclerAdapter<EventCardModel, *>

            adapter.submitItems(cards)
        })
    }

    /**
     * Navigates to event detail
     */
    private fun onEventClickedListener(v: View, model: EventCardModel) {
        navController.navigate(R.id.action_navigation_event_detail, bundleOf(
                EventDetailFragment.ARG_MODEL to model.hid
        ))
    }

    /**
     * Returns function that navigates to [R.id.action_navigation_events_more] with passed mode
     */
    private fun onMoreEventsClickedClosure(mode: Int): (View) -> Unit = {
        navController.navigate(
                R.id.action_navigation_events_more,
                bundleOf(EventsListFragment.ARG_MODE to mode)
        )
    }

    /**
     * Shows/Hides spinner based on presence of elements in passed list
     */
    private fun setLoadingSpinnerVisibility(spinner: ProgressBar, items: List<*>) {
        if (items.isEmpty()) {
            spinner.visibility = View.VISIBLE
        } else {
            spinner.visibility = View.GONE
        }
    }

    private fun handleUpdate(update: UpdateResult) {
        events_refresh.isRefreshing = false

        if (update is UpdateResult.Error) {
            AlertDialog.Builder(context)
                    .setTitle(getString(R.string.error_title))
                    .setMessage(getString(R.string.error_data_not_updated))
                    .create()
                    .show()
        }
    }
}
