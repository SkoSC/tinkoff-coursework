package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.User
import com.skosc.tkffintech.ui.adapter.TasksRecyclerAdapter
import com.skosc.tkffintech.ui.adapter.UsersSpinnerAdapter
import com.skosc.tkffintech.ui.model.toAdapterItems
import com.skosc.tkffintech.viewmodel.grades.GradesSingleUserViewModel
import kotlinx.android.synthetic.main.fragment_course_grades_single.*


class CourseGradesSingleFragment : TKFFragment() {
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
    private val vm by lazy { getViewModel(GradesSingleUserViewModel::class) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_grades_single, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseName = it.getString(ARG_COURSE)
                    ?: throw IllegalStateException("${CourseGradesSingleFragment::class.java.simpleName} " +
                    "requires: $ARG_COURSE argument")
        }
    }

    override fun onStart() {
        super.onStart()
        vm.init(courseName)
        vm.setUser(User(3196, "ME"))
        vm.users.observe(this, Observer {
            grades_user_spinner.adapter = UsersSpinnerAdapter(context!!, it)
        })
        grades_user_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val adapter = grades_user_spinner.adapter as UsersSpinnerAdapter
                val user = adapter.items[position]
                vm.setUser(user)
            }

        }

        grades_list_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = TasksRecyclerAdapter()
        grades_list_recycler.adapter = adapter
        grades_list_recycler.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.grades.observe(this, Observer {
            val items = it.toAdapterItems()
            adapter.submitItems(items)
            adapter.notifyDataSetChanged()
        })
    }
}
