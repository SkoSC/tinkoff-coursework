package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.misc.formatAsRatio
import com.skosc.tkffintech.ui.model.CourseDetailModel
import com.skosc.tkffintech.utils.ratio
import org.joda.time.DateTime

class CoursesDetailedRecyclerAdapter(private val onDetailsClick: (v: View, model: CourseDetailModel) -> Unit) : RecyclerView.Adapter<CoursesDetailedRecyclerAdapter.ViewHolder>() {

    var items = listOf(
            CourseDetailModel("Curse 1", DateTime.now().minusDays(32), score = Ratio(3.0, 10.0)),
            CourseDetailModel("Curse 2", DateTime.now().minusDays(200), score = Ratio(4.0, 12.0)),
            CourseDetailModel("Curse 3", DateTime.now().minusDays(1), score = Ratio(12.0, 12.0))
    )

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
        val lessonsTotal: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_lessons_total) }
        val lessonsProgress: ProgressBar by lazy { view.findViewById<ProgressBar>(R.id.courses_entry_progress_lessons) }
        val lessonsLeft: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_lessons_left) }
        val lessonsTotalMini: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_lessons_total_mini) }
        val moreDetails: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_more) }
        val globalRate: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_global_rate) }
        val testsPassed: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_tests_passed) }
        val homeworkComplited: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_homework_completed) }

        fun bind(model: CourseDetailModel, onDetailsClick: (v: View, model: CourseDetailModel) -> Unit) {
            title.text = model.title
            score.text = model.score.actual.toString()
            scoreProgress.ratio = model.score
            lessonsTotal.text = model.lessons.max.toString()
            lessonsProgress.ratio = model.lessons
            lessonsLeft.text = model.lessons.left.toString()
            lessonsTotalMini.text = model.lessons.max.toString()
            globalRate.text = model.globalRate.formatAsRatio("/")
            testsPassed.text = model.testsPassed.formatAsRatio("/")
            homeworkComplited.text = model.homeworkCompleted.formatAsRatio("/")
            moreDetails.setOnClickListener {
                onDetailsClick.invoke(view, model)
            }
        }
    }
}