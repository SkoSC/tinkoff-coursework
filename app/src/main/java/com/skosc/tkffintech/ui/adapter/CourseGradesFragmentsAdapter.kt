package com.skosc.tkffintech.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.fragment.CourseGradesManyFragment
import com.skosc.tkffintech.ui.fragment.CourseGradesSingleFragment

class CourseGradesFragmentsAdapter(
        course: String,
        fm: FragmentManager,
        private val context: Context) : FragmentPagerAdapter(fm) {
    companion object {
        const val ONLY_2_FRAGMENTS_ALLOWED_ERR = "Only 2 fragments allowed in this adapter"
    }

    private val single = CourseGradesSingleFragment.newInstance(course)
    private val many = CourseGradesManyFragment.newInstance(course)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> single
            1 -> many
            else -> throw IllegalStateException(ONLY_2_FRAGMENTS_ALLOWED_ERR)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.grades_title_single)
            1 -> context.getString(R.string.grades_title_many)
            else -> throw IllegalStateException(ONLY_2_FRAGMENTS_ALLOWED_ERR)
        }
    }

    override fun getCount(): Int = 2

}