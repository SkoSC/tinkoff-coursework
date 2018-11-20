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
import com.skosc.tkffintech.ui.adapter.CoursesDetailedRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.CoursesPreviewRecyclerAdapter
import com.skosc.tkffintech.ui.model.CourseDetailModel
import com.skosc.tkffintech.ui.model.CoursePreviewModel
import com.skosc.tkffintech.viewmodel.courses.CourseViewModel
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.incl_courses_list_vertical.*

class CoursesFragment : TKFFragment() {
    private val navController by lazy { Navigation.findNavController(courses_all_recycler) }
    private val vm by lazy { getViewModel(CourseViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_courses, container, false)
    }

    override fun onStart() {
        super.onStart()

        vm.checkForUpdate()

        courses_all_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        courses_all_recycler.adapter = CoursesPreviewRecyclerAdapter { _, model ->
            navController.navigate(R.id.navigation_course_grades, bundleOf(
                    CourseGradesFragment.ARG_COURSE to model.url
            ))
        }
        courses_all_recycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        courses_fresh_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        courses_fresh_recycler.adapter = CoursesDetailedRecyclerAdapter { _, model ->
            navController.navigate(R.id.navigation_course_detail, bundleOf(
                    CourseDetailFragment.ARG_COURSE to model.url
            ))
        }


        vm.allCourses.observe(this, Observer {
            courses_refresh.isRefreshing = false
            val adapter = courses_all_recycler.adapter as CoursesPreviewRecyclerAdapter
            adapter.items = it.map { info ->
                CoursePreviewModel(
                        url = info.url,
                        title = info.title,
                        status = info.status,
                        date = info.starts
                )
            }
            adapter.notifyDataSetChanged()
        })

        vm.activeCourses.observe(this, Observer {
            courses_refresh.isRefreshing = false
            val adapter = courses_fresh_recycler.adapter as CoursesDetailedRecyclerAdapter
            adapter.items = it.map { info ->
                CourseDetailModel(
                        title = info.info.title,
                        url = info.info.url,
                        homeworkCompleted = info.statistics.homeworkRatio,
                        testsPassed = info.statistics.testRatio,
                        scoreRate = info.statistics.scoreRatio,
                        completionRatio = info.statistics.completionRatio
                )
            }
            adapter.notifyDataSetChanged()
        })

        courses_refresh.setOnRefreshListener {
            vm.forceUpdate()
        }
    }

}
