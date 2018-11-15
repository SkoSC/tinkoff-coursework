package com.skosc.tkffintech.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.CourseGradesAdapter
import kotlinx.android.synthetic.main.fragment_course_grades.*

class CourseGradesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_grades, container, false)
    }

    override fun onStart() {
        super.onStart()
        course_grades_pager.adapter = CourseGradesAdapter(childFragmentManager)
    }
}
