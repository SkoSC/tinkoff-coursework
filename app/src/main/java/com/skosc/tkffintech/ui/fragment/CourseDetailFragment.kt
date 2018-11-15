package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.HomeworkGrade
import com.skosc.tkffintech.entities.HomeworkTask
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.ui.adapter.TasksRecyclerAdapter
import com.skosc.tkffintech.ui.model.TaskAdapterItem
import com.skosc.tkffintech.viewmodel.coursedetail.CourseDetailViewModel
import kotlinx.android.synthetic.main.fragment_course_detail.*


class CourseDetailFragment : TKFFragment() {
    private val navController by lazy { Navigation.findNavController(course_detail_tasks) }

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
        vm.grades.observe(this, Observer {
            adapter.items = it.toAdapterItems()
            adapter.notifyDataSetChanged()
        })

        courses_entry_score_progress.setOnClickListener {
            navController.navigate(R.id.action_navigation_course_grades)
        }
    }

    private fun List<CourseDetailViewModel.HomeworkWithGrades>.toAdapterItems(): List<TaskAdapterItem> {
        return this.flatMap { listOf(TaskAdapterItem.Header(it.homework.title)) + it.grades.toAdapterItems() }
    }

    @JvmName("tasksToAdapterItems")
    private fun List<Pair<HomeworkTask, HomeworkGrade>>.toAdapterItems(): List<TaskAdapterItem> {
        return this.map { pair ->
            val (task, grade) = pair
            TaskAdapterItem.Entry(
                    task.title,
                    Ratio(
                            grade.mark.toDouble(),
                            task.maxScore.toDouble()
                    ),
                    task.deadlineDate.toString(),
                    grade.status
            )
        }
    }
}
