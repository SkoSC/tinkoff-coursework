package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.EventCardModel

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


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EventDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_MODEL, param2)
                    }
                }
    }
}
