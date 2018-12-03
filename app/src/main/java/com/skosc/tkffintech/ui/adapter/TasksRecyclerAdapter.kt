package com.skosc.tkffintech.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.misc.resolver.ChipColors
import com.skosc.tkffintech.ui.model.TaskAdapterItem
import com.skosc.tkffintech.ui.view.ScoreView
import com.skosc.tkffintech.utils.GenericDiffUtilCallback
import com.skosc.tkffintech.utils.extensions.getColorCompat

class TasksRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ENTRY = 1
    }

    private val differ = AsyncListDiffer(this, GenericDiffUtilCallback<TaskAdapterItem>())

    init {
        setHasStableIds(true)
    }

    fun submitItems(items: List<TaskAdapterItem>) {
        differ.submitList(items)
    }

    override fun getItemViewType(position: Int): Int = when (differ.currentList[position]) {
        is TaskAdapterItem.Header -> TYPE_HEADER
        is TaskAdapterItem.Entry -> TYPE_ENTRY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ENTRY -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_course_task, parent, false)
                ViewHolderEntry(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_course_task_header, parent, false)
                ViewHolderHeader(view)
            }
            else -> throw IllegalArgumentException("Unsupported type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.bind(differ.currentList[position] as TaskAdapterItem.Header)
        }

        if (holder is ViewHolderEntry) {
            holder.bind(differ.currentList[position] as TaskAdapterItem.Entry)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemId(position: Int): Long {
        return differ.currentList[position].hashCode().toLong()
    }

    class ViewHolderHeader(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title by lazy { view.findViewById<TextView>(R.id.task_header_title) }

        fun bind(header: TaskAdapterItem.Header) {
            title.text = header.title
        }
    }

    class ViewHolderEntry(private val view: View) : RecyclerView.ViewHolder(view) {
        private val score by lazy { view.findViewById<ScoreView>(R.id.task_icon) }
        private val title by lazy { view.findViewById<TextView>(R.id.task_title) }
        private val info by lazy { view.findViewById<TextView>(R.id.task_info) }
        private val status by lazy { view.findViewById<TextView>(R.id.task_status) }

        fun bind(entry: TaskAdapterItem.Entry) {
            title.text = entry.title
            score.text = entry.score.actual.toString()

            score.setBgColor(entry.status.toBgColor())
            score.setBoarderColor(entry.status.toBorderColor())

            status.backgroundTintList = entry.status.toChipColor()
            status.text = view.context.getString(entry.status.resourse)

            info.text = entry.info
        }

        private fun HomeworkStatus.toChipColor(): ColorStateList {
            val statusColorId = ChipColors.colorForHomeworkStatus(this)
            val colorValue = view.context.getColorCompat(statusColorId)
            return ColorStateList.valueOf(colorValue)
        }

        @ColorInt
        private fun HomeworkStatus.toBorderColor(): Int {
            return when (this) {
                HomeworkStatus.NEW -> view.context.getColorCompat(R.color.task_score_inwork)
                HomeworkStatus.ACCEPTED -> view.context.getColorCompat(R.color.task_score_success)
                HomeworkStatus.TEST_RESULT -> view.context.getColorCompat(R.color.task_score_success)
                HomeworkStatus.UNKNOWN -> view.context.getColorCompat(R.color.task_score_failed)
            }
        }

        @ColorInt
        private fun HomeworkStatus.toBgColor(): Int {
            return when (this) {
                HomeworkStatus.NEW -> view.context.getColorCompat(R.color.task_score_inwork_bg)
                HomeworkStatus.ACCEPTED -> view.context.getColorCompat(R.color.task_score_success_bg)
                HomeworkStatus.TEST_RESULT -> view.context.getColorCompat(R.color.task_score_success_bg)
                HomeworkStatus.UNKNOWN -> view.context.getColorCompat(R.color.task_score_failed_bg)
            }
        }
    }
}