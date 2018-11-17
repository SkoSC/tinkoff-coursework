package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.TasksRecyclerAdapter
import com.skosc.tkffintech.ui.model.toAdapterItems
import com.skosc.tkffintech.utils.NumberFormatter
import com.skosc.tkffintech.viewmodel.coursedetail.CourseDetailViewModel
import kotlinx.android.synthetic.main.card_profile_stats.*
import kotlinx.android.synthetic.main.fragment_course_detail.*


class CourseDetailFragment : TKFFragment() {
    companion object {
        const val ARG_COURSE = "course_name"
    }

    private lateinit var course: String

    private val navController by lazy { Navigation.findNavController(course_detail_tasks) }

    private val vm by lazy {
        getViewModel(CourseDetailViewModel::class, mapOf(
                CourseDetailViewModel.COURSE_ARG to course
        ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getString(ARG_COURSE)
                    ?: throw IllegalStateException("${CourseDetailFragment::class.java.simpleName} " +
                    "requires: $ARG_COURSE argument")
        }
    }

    override fun onStart() {
        super.onStart()
        course_detail_tasks.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = TasksRecyclerAdapter()
        course_detail_tasks.adapter = adapter
        course_detail_tasks.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.grades.observe(this, Observer {
            val items = it.toAdapterItems()
            adapter.submitItems(items)
            adapter.notifyDataSetChanged()
        })

        courses_entry_score_progress.setOnClickListener {
            navController.navigate(R.id.action_navigation_course_grades, bundleOf(
                    "course_name" to course
            ))
        }

        card_stats_left_slot_text.text = getString(R.string.stats_score)
        vm.statsScore.observe(this, Observer {
            card_stats_left_slot.text = NumberFormatter.userScore(it)
        })

        card_stats_center_slot_text.text = getString(R.string.stats_tests)
        vm.statsTests.observe(this, Observer {
            card_stats_center_slot.text = it.toString()
        })

        card_stats_right_slot_text.text = getString(R.string.stats_homeworks)
        vm.statsHomeWorks.observe(this, Observer {
            card_stats_right_slot.text = it.toString()
        })
    }
}
