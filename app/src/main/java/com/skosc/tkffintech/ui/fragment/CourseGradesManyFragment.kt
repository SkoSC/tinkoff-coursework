package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.GradesSumRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.SortSpinnerAdapter
import com.skosc.tkffintech.viewmodel.grades.GradesManyUserViewModel
import kotlinx.android.synthetic.main.fragment_course_grades_many.*

class CourseGradesManyFragment : TKFFragment() {
    companion object {
        private const val ARG_COURSE = "course_name"

        fun newInstance(course: String): CourseGradesSingleFragment {
            return CourseGradesSingleFragment().apply {
                arguments = bundleOf(
                        ARG_COURSE to course
                )
            }
        }
    }

    private lateinit var courseName: String
    private val vm by lazy { getViewModel(GradesManyUserViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_grades_many, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseName = it.getString(ARG_COURSE)
                    ?: throw IllegalStateException("${CourseGradesManyFragment::class.java.simpleName} " +
                    "requires: $ARG_COURSE argument")
        }
    }

    override fun onStart() {
        super.onStart()
        vm.init("android_fall2018")
        vm.sorters.observe(this, Observer {
            grades_sort_spinner.adapter = SortSpinnerAdapter(context!!, it)
        })
        grades_sort_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sorter = vm.sorters.value!![position]
                vm.setSorter(sorter)
            }

        }

        grades_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = GradesSumRecyclerAdapter()
        grades_recycler.adapter = adapter
        vm.userWithGradesSum.observe(this, Observer {
            adapter.submitItems(it)
        })
    }


}
