package com.skosc.tkffintech.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

import com.skosc.tkffintech.R
import kotlinx.android.synthetic.main.fragment_event_detail.*
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import androidx.lifecycle.Observer
import com.skosc.tkffintech.viewmodel.eventdetail.EventDetailViewModel
import java.util.*
import com.google.android.gms.maps.model.LatLngBounds
import android.location.Address
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.skosc.tkffintech.utils.DateTimeFormatter


class EventDetailFragment : TKFFragment() {
    companion object {
        const val ARG_MODEL = "model_hid"
    }

    private val vm by lazy { getViewModel(EventDetailViewModel::class) }

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
        val geocoder = Geocoder(context)
        val latlongbuilder = LatLngBounds.builder()
        places.split(";")
                .map { it.trim().toLowerCase() }
                .map { geocoder.getFromLocationName(it, 1) }
                .map { it.first() }
                .map { LatLng(it.latitude, it.longitude) }
                .forEach {
                    map.addMarker(MarkerOptions().position(it))
                    latlongbuilder.include(it)
                }
        val bounds = latlongbuilder.build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.10).toInt() // offset from edges of the map 10% of screen

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding))
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
        event_detail_map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        event_detail_map.onLowMemory()
    }

}
