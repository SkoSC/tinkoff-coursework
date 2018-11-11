package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.ui.adapter.TasksRecyclerAdapter
import com.skosc.tkffintech.ui.model.TaskAdapterItem
import com.skosc.tkffintech.viewmodel.coursedetail.CourseDetailViewModel
import kotlinx.android.synthetic.main.fragment_course_detail.*


class CourseDetailFragment : TKFFragment() {

    private val vm by lazy { getViewModel(CourseDetailViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }


    override fun onStart() {
        super.onStart()
        course_detail_tasks.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = TasksRecyclerAdapter()
        course_detail_tasks.adapter = adapter
        course_detail_tasks.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.homeworks.observe(this, Observer {
            adapter.items = it.toAdapterItems()
            adapter.notifyDataSetChanged()
        })
    }

    private fun List<Homework>.toAdapterItems(): List<TaskAdapterItem> {
        return this.flatMap { listOf(TaskAdapterItem.Header(it.title)) + it.tasks.toAdapterItems() }
    }

    @JvmName("tasksToAdapterItems")
    private fun List<HomeworkTask>.toAdapterItems(): List<TaskAdapterItem> {
        return this.map {
            TaskAdapterItem.Entry(
                    it.title,
                    Ratio(
                            it.maxScore.toDouble().toInt(),
                            it.maxScore.toDouble().toInt()
                    ),
                    it.deadlineDate.toString()
            )
        }
    }
}
