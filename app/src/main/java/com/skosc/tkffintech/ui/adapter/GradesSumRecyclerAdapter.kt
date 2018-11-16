package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.viewmodel.UserWithGradesSum

class GradesSumRecyclerAdapter : RecyclerView.Adapter<GradesSumRecyclerAdapter.ViewHolder>() {
    var items: List<UserWithGradesSum> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_grades_sum, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val username: TextView by lazy { view.findViewById<TextView>(R.id.grades_sum_username) }
        private val scoreSum: TextView by lazy { view.findViewById<TextView>(R.id.grades_sum_value) }

        fun bind(model: UserWithGradesSum) {
            username.text = model.user.name
            scoreSum.text = model.gradesSum.toString()
        }
    }
}