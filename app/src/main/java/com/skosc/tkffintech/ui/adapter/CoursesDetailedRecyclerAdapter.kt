package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.utils.formatting.formatAsRatio
import com.skosc.tkffintech.ui.model.CourseDetailModel
import com.skosc.tkffintech.utils.extensions.ratio

class CoursesDetailedRecyclerAdapter(private val onDetailsClick: (v: View, model: CourseDetailModel) -> Unit) : RecyclerView.Adapter<CoursesDetailedRecyclerAdapter.ViewHolder>() {

    var items = listOf<CourseDetailModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_course_with_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model, onDetailsClick)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_title) }
        val score: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_date) }
        val scoreProgress: ProgressBar by lazy { view.findViewById<ProgressBar>(R.id.courses_entry_score_progress) }
        val moreDetails: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_more) }
        val globalRate: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_global_rate) }
        val testsPassed: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_tests_passed) }
        val homeworkComplited: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_homework_completed) }

        fun bind(model: CourseDetailModel, onDetailsClick: (v: View, model: CourseDetailModel) -> Unit) {
            title.text = model.title
            score.text = model.scoreRate.actual.toString()
            scoreProgress.ratio = model.completionRatio
            globalRate.text = model.scoreRate.formatAsRatio("/")
            testsPassed.text = model.testsPassed.formatAsRatio("/")
            homeworkComplited.text = model.homeworkCompleted.formatAsRatio("/")
            moreDetails.setOnClickListener {
                onDetailsClick.invoke(view, model)
            }
        }
    }
}