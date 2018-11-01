package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R

class ActualEventsRecyclerAdapter : RecyclerView.Adapter<ActualEventsRecyclerAdapter.ViewHolder>() {
    val items = listOf(1, 2, 3, 4, 5, 6, 7, 8)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_event_vertical, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // pass
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {}
}