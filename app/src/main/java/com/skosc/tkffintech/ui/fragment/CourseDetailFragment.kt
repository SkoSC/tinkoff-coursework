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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.ui.adapter.TasksRecyclerAdapter
import com.skosc.tkffintech.ui.model.toAdapterItems
import com.skosc.tkffintech.utils.extensions.observe
import com.skosc.tkffintech.utils.formatting.NumberFormatter
import com.skosc.tkffintech.viewmodel.coursedetail.CourseDetailViewModel
import kotlinx.android.synthetic.main.card_profile_stats.*
import kotlinx.android.synthetic.main.fragment_course_detail.*


class CourseDetailFragment : TKFFragment() {
    companion object {
        const val ARG_COURSE = "course"
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
        vm.checkForUpdates().observe(this, this::handleUpdate)

        setupGradesList()
        setupTopIcon()
        setupStatisticsCard()
        setupRefresh()
    }

    private fun setupRefresh() {
        course_detail_refresh.setOnRefreshListener {
            vm.forceRefresh().observe(this, Observer { handleUpdate(it) })
        }
    }

    private fun setupTopIcon() {
        course_detail_top_image.setOnClickListener {
            navController.navigate(R.id.action_navigation_course_grades, bundleOf(
                    ARG_COURSE to course
            ))
        }
        vm.topIcon.observe(this, Observer {
            if (it != null) {
                course_detail_top_progress.animate()
                        .alpha(0F)
                        .setDuration(500)
                        .start()

                Glide.with(this)
                        .load(it)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(course_detail_top_image)
            } else {
                course_detail_top_progress.animate()
                        .alpha(1F)
                        .setDuration(500)
                        .start()
                course
            }
        })
    }

    private fun setupGradesList() {
        course_detail_tasks.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = TasksRecyclerAdapter()
        course_detail_tasks.adapter = adapter
        course_detail_tasks.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.grades.observe(this, Observer {
            course_detail_empty_msg.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

            val items = it.toAdapterItems()
            adapter.submitItems(items)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupStatisticsCard() {
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

    private fun handleUpdate(status: UpdateResult) {
        course_detail_refresh.isRefreshing = false
    }
}
