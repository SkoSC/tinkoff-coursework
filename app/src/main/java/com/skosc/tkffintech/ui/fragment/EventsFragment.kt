package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.ActualEventsRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onStart() {
        super.onStart()
        events_actual_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        events_actual_recycler.adapter = ActualEventsRecyclerAdapter()
    }
}
