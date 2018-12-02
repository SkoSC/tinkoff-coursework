package com.skosc.tkffintech.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.GeoAddress
import com.skosc.tkffintech.utils.extensions.observe
import com.skosc.tkffintech.utils.formatting.DateTimeFormatter
import com.skosc.tkffintech.viewmodel.eventdetail.EventDetailViewModel
import kotlinx.android.synthetic.main.fragment_event_detail.*


class EventDetailFragment : TKFFragment() {
    companion object {
        const val ARG_MODEL = "model_hid"
        const val DEFAULT_MAP_PADDING_MULTI = 10
        const val DEFAULT_MAP_ZOOM_SINGLE = 12f
    }

    private lateinit var vm: EventDetailViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)
        view.findViewById<MapView>(R.id.event_detail_map).onCreate(savedInstanceState)
        return view
    }


    override fun onStart() {
        super.onStart()
        event_detail_map.onStart()

        arguments?.let {
            vm = getViewModel(EventDetailViewModel::class, mapOf(
                    EventDetailViewModel.ARG_HID to it.getLong(ARG_MODEL).toString()
            ))
        }

        vm.title.observe(this, Observer {
            event_detail_title.text = it
        })

        vm.description.observe(this) {
            event_detail_description.text = it
        }
        vm.eventDates.observe(this, this::showEventDates)

        vm.place.observe(this) { place ->
            event_detail_place.text = place
        }

        vm.addresses.observe(this) { addresses ->
            event_detail_map.getMapAsync(onMapReady(addresses))
        }
    }

    private fun onMapReady(addresses: List<GeoAddress>) = { map: GoogleMap ->
        try {
            setupMap(map, addresses)
        } catch (e: Exception) {
            fallbackSetupMap()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showEventDates(eventDates: EventDetailViewModel.EventDates) {
        val from = DateTimeFormatter.DATE_FORMATTER_SHORT_EU.print(eventDates.from)
        val to = DateTimeFormatter.DATE_FORMATTER_SHORT_EU.print(eventDates.to)
        event_detail_short_info.text = "$from - $to"
    }

    private fun fallbackSetupMap() {
        event_detail_place_card.visibility = View.GONE
    }

    private fun setupMap(map: GoogleMap, addresses: List<GeoAddress>) {
        map.setOnMarkerClickListener { true }

        if (addresses.size > 1) {
            showMultipleLocations(map, addresses)
        } else if (addresses.size == 1) {
            showSingleLocation(map, addresses.first())
        }
    }

    private fun showMultipleLocations(map: GoogleMap, addresses: List<GeoAddress>) {
        val latLngBounds = LatLngBounds.Builder()
        addresses.forEach { latLngBounds.include(it.latlon) }

        addresses.forEach {
            map.addMarker(MarkerOptions()
                    .position(it.latlon)
                    .title(it.title)
            )
        }

        val bounds = latLngBounds.build()

        val cu = CameraUpdateFactory.newLatLngBounds(bounds, DEFAULT_MAP_PADDING_MULTI)
        map.animateCamera(cu)
    }

    private fun showSingleLocation(map: GoogleMap, address: GeoAddress) {
        val latLng = address.latlon

        map.addMarker(MarkerOptions()
                .position(latLng)
                .title(address.title)
        )


        val cu = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM_SINGLE)
        map.animateCamera(cu)
    }

    override fun onStop() {
        super.onStop()
        event_detail_map.onStop()
    }

    override fun onPause() {
        super.onPause()
        event_detail_map.onPause()
    }

    override fun onResume() {
        super.onResume()
        event_detail_map.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        event_detail_map?.let { onDestroy() }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        event_detail_map.onLowMemory()
    }

    private val GeoAddress.latlon get() = LatLng(lat, lon)
}
