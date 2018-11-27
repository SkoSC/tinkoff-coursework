package com.skosc.tkffintech.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.ProfileField

class ProfileAttributeEditAdapter : RecyclerView.Adapter<ProfileAttributeEditAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, ProfileFieldAttributeDiffCallback)
    val items: List<ProfileField> get() = differ.currentList

    fun submitItems(items: List<ProfileField>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_user_info_section_entry_edit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title by lazy { view.findViewById<TextView>(R.id.profile_attribute_title) }
        val value by lazy { view.findViewById<EditText>(R.id.profile_attribute_value) }

        fun bind(model: ProfileField) {
            title.text = view.context.getString(model.header)
            value.setText(model.value)
            value.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    model.value = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}