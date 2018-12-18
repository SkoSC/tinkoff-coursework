package com.skosc.tkffintech.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.CourseGradesFragmentsAdapter
import kotlinx.android.synthetic.main.fragment_course_grades.*

class CourseGradesFragment : Fragment() {
    companion object {
        const val ARG_COURSE = "course_name"
    }

    private lateinit var course: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_grades, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getString(ARG_COURSE)
                    ?: throw IllegalStateException("${CourseGradesFragment::class.java.simpleName} " +
                    "requires: $ARG_COURSE argument")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        course_grades_pager.adapter = CourseGradesFragmentsAdapter(course, childFragmentManager, context!!)
    }

}
