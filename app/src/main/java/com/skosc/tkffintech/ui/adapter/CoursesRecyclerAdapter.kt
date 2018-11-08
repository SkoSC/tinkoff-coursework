package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.ui.model.CurseModel
import com.skosc.tkffintech.R
import java.lang.IllegalStateException

class CoursesRecyclerAdapter(private val mode: Int): RecyclerView.Adapter<CoursesRecyclerAdapter.ViewHolder>() {
    companion object {
        const val MODE_VERTICAL = 0
        const val MODE_HORIZONTAL = 1
    }

    var items = listOf<CurseModel>(
            CurseModel("Curse 1"),
            CurseModel("Curse 2"),
            CurseModel("Curse 3")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when(mode) {
            MODE_VERTICAL -> R.layout.card_course
            MODE_HORIZONTAL -> R.layout.card_course_with_details
            else -> throw IllegalStateException("Unsupported mode: $mode")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView? by lazy { view.findViewById<TextView>(R.id.courses_entry_status) }

        fun bind(model: CurseModel) {
            if (title != null){
                title?.text = model.title
            }
        }
    }
}