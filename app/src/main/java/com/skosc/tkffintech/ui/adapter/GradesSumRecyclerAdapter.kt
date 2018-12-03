package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.utils.GenericDiffutilCallback
import com.skosc.tkffintech.utils.formatting.NumberFormatter

class GradesSumRecyclerAdapter : RecyclerView.Adapter<GradesSumRecyclerAdapter.ViewHolder>() {
    private val differ = AsyncListDiffer(this, GenericDiffutilCallback<UserWithGradesSum>())

    fun submitItems(items: List<UserWithGradesSum>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_grades_sum, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(position, model)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val username: TextView by lazy { view.findViewById<TextView>(R.id.grades_sum_username) }
        private val scoreSum: TextView by lazy { view.findViewById<TextView>(R.id.grades_sum_value) }
        private val position: TextView by lazy { view.findViewById<TextView>(R.id.grades_sum_position) }

        fun bind(pos: Int, model: UserWithGradesSum) {
            position.text = pos.toString()
            username.text = model.user.name
            scoreSum.text = NumberFormatter.userScore(model.gradesSum)
        }
    }
}