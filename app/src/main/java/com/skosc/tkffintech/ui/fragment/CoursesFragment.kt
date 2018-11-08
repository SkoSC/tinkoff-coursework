package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.CoursesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.incl_cources_list_vertical.*

class CoursesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_courses, container, false)
    }

    override fun onStart() {
        super.onStart()
        courses_all_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        courses_all_recycler.adapter = CoursesRecyclerAdapter(CoursesRecyclerAdapter.MODE_VERTICAL)

        courses_fresh_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        courses_fresh_recycler.adapter = CoursesRecyclerAdapter(CoursesRecyclerAdapter.MODE_HORIZONTAL)

    }
}
