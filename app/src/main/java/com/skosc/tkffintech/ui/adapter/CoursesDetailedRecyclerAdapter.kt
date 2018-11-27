package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.ui.model.CourseDetailModel
import com.skosc.tkffintech.utils.extensions.ratio
import com.skosc.tkffintech.utils.formatting.NumberFormatter
import com.skosc.tkffintech.utils.formatting.formatAsRatio
import com.skosc.tkffintech.utils.formatting.formatAsScoreRatio

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
        private val title: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_title) }
        private val score: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_date) }
        private val scoreProgress: ProgressBar by lazy { view.findViewById<ProgressBar>(R.id.courses_entry_score_progress) }
        private val moreDetails: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_more) }
        private val globalRate: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_global_rate) }
        private val testsPassed: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_tests_passed) }
        private val homeworkCompleted: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_homework_completed) }

        fun bind(model: CourseDetailModel, onDetailsClick: (v: View, model: CourseDetailModel) -> Unit) {
            title.text = model.title
            score.text = formatIfNotMissing(model.scoreRate) { NumberFormatter.userScore(it.actual) }
            scoreProgress.ratio = model.scoreRate
            globalRate.text = formatIfNotMissing(model.scoreRate) { it.formatAsScoreRatio() }
            testsPassed.text = formatIfNotMissing(model.testsPassed) { it.formatAsRatio() }
            homeworkCompleted.text = formatIfNotMissing(model.homeworkCompleted) { it.formatAsRatio() }
            moreDetails.setOnClickListener {
                onDetailsClick.invoke(view, model)
            }
        }

        private inline fun formatIfNotMissing(ratio: Ratio, formatter: (Ratio) -> String): String {
            return if (ratio == Ratio(0, 0)) {
                "?"
            } else {
                formatter(ratio)
            }
        }
    }
}