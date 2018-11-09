package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.CurseStatus
import com.skosc.tkffintech.ui.model.CursePreviewModel
import org.joda.time.DateTime

class CoursesPreviewRecyclerAdapter() : RecyclerView.Adapter<CoursesPreviewRecyclerAdapter.ViewHolder>() {

    var items = listOf<CursePreviewModel>(
            CursePreviewModel("Curse 1", DateTime.now().minusDays(32), 23, CurseStatus.PASSED),
            CursePreviewModel("Curse 2", DateTime.now().minusDays(200), 198, CurseStatus.FAILED),
            CursePreviewModel("Curse 3", DateTime.now().minusDays(1), 7, CurseStatus.ONGOING)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_course, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView? by lazy { view.findViewById<TextView>(R.id.courses_entry_title) }
        val status: TextView? by lazy { view.findViewById<TextView>(R.id.courses_entry_status) }
        val score: TextView? by lazy { view.findViewById<TextView>(R.id.courses_entry_score) }
        val date: TextView? by lazy { view.findViewById<TextView>(R.id.courses_entry_date) }

        fun bind(model: CursePreviewModel) {
            title?.text = model.title
            status?.text = model.status.name
            score?.text = model.score.toString()
            date?.text = model.date.toString()
        }
    }
}