package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.TaskAdapterItem
import com.skosc.tkffintech.ui.view.ScoreView
import java.lang.IllegalArgumentException

class TasksRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ENTRY = 1
    }

    var items = listOf(
            TaskAdapterItem.Header("Header 1"),
            TaskAdapterItem.Entry("Entry 1")
            )

    override fun getItemViewType(position: Int): Int = when(items[position]) {
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
            TYPE_HEADER -> ViewHolderHeader(TextView(parent.context))
            else -> throw IllegalArgumentException("Unsupported type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.bind(items[position] as TaskAdapterItem.Header)
        }

        if (holder is ViewHolderEntry) {
            holder.bind(items[position] as TaskAdapterItem.Entry)
        }
    }

    override fun getItemCount(): Int = items.size



    class ViewHolderHeader(private val view : TextView): RecyclerView.ViewHolder(view) {
        fun bind(header: TaskAdapterItem.Header) {
            view.text = header.title
        }
    }

    class ViewHolderEntry(private val view: View) : RecyclerView.ViewHolder(view) {
        private val score by lazy { view.findViewById<ScoreView>(R.id.task_score) }
        private val title by lazy { view.findViewById<TextView>(R.id.task_title) }
        private val info by lazy { view.findViewById<TextView>(R.id.task_info) }
        private val status by lazy { view.findViewById<TextView>(R.id.task_status) }

        fun bind(entry: TaskAdapterItem.Entry) {
            title.text = entry.title
        }
    }
}