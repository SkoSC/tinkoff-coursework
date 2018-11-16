package com.skosc.tkffintech.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.skosc.tkffintech.ui.fragment.CourseGradesManyFragment
import com.skosc.tkffintech.ui.fragment.CourseGradesSingleFragment

class CourseGradesFragmentsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    companion object {
        const val ONLY_2_FRAGMENTS_ALLOWED_ERR = "Only 2 fragments allowed in this adapter"
    }

    private val single = CourseGradesSingleFragment.newInstance("android_fall2018")
    private val many = CourseGradesManyFragment()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> single
            1 -> many
            else -> throw IllegalStateException(ONLY_2_FRAGMENTS_ALLOWED_ERR)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Single"
            1 -> "Many"
            else -> throw IllegalStateException(ONLY_2_FRAGMENTS_ALLOWED_ERR)
        }
    }

    override fun getCount(): Int = 2

}