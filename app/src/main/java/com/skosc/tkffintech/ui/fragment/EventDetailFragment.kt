package com.skosc.tkffintech.ui.fragment


import android.location.Geocoder
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
import com.skosc.tkffintech.utils.formatting.DateTimeFormatter
import com.skosc.tkffintech.viewmodel.eventdetail.EventDetailViewModel
import kotlinx.android.synthetic.main.fragment_event_detail.*


class EventDetailFragment : TKFFragment() {
    companion object {
        const val ARG_MODEL = "model_hid"
        const val DEFAULT_MAP_PADDING = 10
    }

    private val vm by lazy { getViewModel(EventDetailViewModel::class) }
    private val geocoder by lazy { Geocoder(context) }

    private var modelHid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            modelHid = it.getLong(ARG_MODEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)
        view.findViewById<MapView>(R.id.event_detail_map).onCreate(savedInstanceState)
        return view
    }


    override fun onStart() {
        super.onStart()
        event_detail_map.onStart()

        vm.init(modelHid)
        vm.title.observe(this, Observer {
            event_detail_title.text = it
        })

        vm.description.observe(this, Observer {
            event_detail_description.text = it
        })
        vm.eventDates.observe(this, Observer {
            val from = DateTimeFormatter.DATE_FORMATTER_SHORT_EU.print(it.from)
            val to = DateTimeFormatter.DATE_FORMATTER_SHORT_EU.print(it.to)
            event_detail_short_info.text = "$from - $to"
        })

        vm.place.observe(this, Observer { place ->
            event_detail_place.text = place
            event_detail_map.getMapAsync(onMapReady(place))
        })
    }

    private fun onMapReady(places: String) = { map: GoogleMap ->
        try {
            setupMap(map, places)
        } catch (e: Exception) {
            fallbackSetupMap()
        }
    }

    private fun fallbackSetupMap() {
        event_detail_place_card.visibility = View.GONE
    }

    private fun setupMap(map: GoogleMap, places: String) {
        data class Location(val title: String, val latLng: LatLng)
        map.setOnMarkerClickListener { true }

        val latLngBounds = LatLngBounds.Builder()

        val addresses = places.split(";", ",", "и")
                .map { rowPlace -> rowPlace.trim().toLowerCase() }
                .map { name -> geocoder.getFromLocationName(name, 1) }
                .filter { addresses -> !addresses.isEmpty() }
                .map { addresses -> addresses.first() }
                .map { address -> Location(address.featureName, LatLng(address.latitude, address.longitude)) }
        addresses.forEach { latLngBounds.include(it.latLng) }
        addresses.forEach {
            map.addMarker(MarkerOptions()
                    .position(it.latLng)
                    .title(it.title)
            )
        }

        if (!addresses.isEmpty()) {
            val bounds = latLngBounds.build()

            val cu = CameraUpdateFactory.newLatLngBounds(bounds, DEFAULT_MAP_PADDING)
            map.animateCamera(cu)

        }
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

}
