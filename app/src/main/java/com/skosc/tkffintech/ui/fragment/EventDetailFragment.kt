package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.EventCardModel
import kotlinx.android.synthetic.main.fragment_event_detail.*
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import java.util.*


private const val ARG_MODEL = "model"

class EventDetailFragment : Fragment() {
    private lateinit var model: EventCardModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model = it.getSerializable(ARG_MODEL) as EventCardModel
            model.date
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }


    override fun onStart() {
        super.onStart()
        event_detail_map.onCreate(null)
        event_detail_map.getMapAsync {
            it.uiSettings.setAllGesturesEnabled(false)
            val geoCoder = Geocoder(context, Locale.getDefault())
            val address = geoCoder.getFromLocationName("Москва", 1).first()
            val seattle = LatLng(address.latitude, address.longitude)
            it.addMarker(MarkerOptions().position(seattle).title("Москва"))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(seattle, 12F))

            event_detail_map.onResume()
        }
    }


}
