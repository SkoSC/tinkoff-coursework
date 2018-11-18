package com.skosc.tkffintech.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.CourseInfo
import com.skosc.tkffintech.entities.CourseInfo.Status.ONGOING
import com.skosc.tkffintech.entities.CourseInfo.Status.UNKNOWN
import com.skosc.tkffintech.ui.model.CoursePreviewModel
import com.skosc.tkffintech.utils.DateTimeFormatter
import com.skosc.tkffintech.utils.getColorCompat

class CoursesPreviewRecyclerAdapter : RecyclerView.Adapter<CoursesPreviewRecyclerAdapter.ViewHolder>() {

    var items = listOf<CoursePreviewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_course, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_title) }
        private val status: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_status) }
        private val date: TextView by lazy { view.findViewById<TextView>(R.id.courses_entry_date) }

        fun bind(model: CoursePreviewModel) {
            title.text = model.title
            setupStatus(model.status)
            date.text = DateTimeFormatter.DATE_FORMATTER_SHORT_EU.print(model.date)
        }

        private fun setupStatus(courseStatus: CourseInfo.Status) {
            val (text, color) = when (courseStatus) {
                ONGOING -> resolveStatusStyle(R.string.course_status_ongoing, R.color.event_chip_green)
                UNKNOWN -> resolveStatusStyle(R.string.course_status_ongoing, R.color.event_chip_red)
            }

            status.text = text
            status.backgroundTintList = ColorStateList.valueOf(color)
        }

        private fun resolveStatusStyle(@StringRes text: Int, @ColorRes color: Int) = Pair(
                view.context.getString(text),
                view.context.getColorCompat(color)
        )
    }
}