package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.model.UserInfoAttribute

class ProfileAttributeAdapter : RecyclerView.Adapter<ProfileAttributeAdapter.ViewHolder>() {
    private val differ = AsyncListDiffer(this, UserInfoAttributeDiffCallback)

    fun submitItems(items: List<UserInfoAttribute>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_user_info_section_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title by lazy { view.findViewById<TextView>(R.id.profile_attribute_title) }
        val value by lazy { view.findViewById<TextView>(R.id.profile_attribute_value) }

        fun bind(model: UserInfoAttribute) {
            title.text = view.context.getString(model.fieldName)
            value.text = model.value
        }
    }
}

private object UserInfoAttributeDiffCallback : DiffUtil.ItemCallback<UserInfoAttribute>() {
    override fun areItemsTheSame(oldItem: UserInfoAttribute, newItem: UserInfoAttribute): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: UserInfoAttribute, newItem: UserInfoAttribute): Boolean {
        return oldItem == newItem
    }

}