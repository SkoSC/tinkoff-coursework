package com.skosc.tkffintech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.model.ProfileField
import com.skosc.tkffintech.utils.GenericDiffutilCallback
import com.skosc.tkffintech.utils.extensions.addAfterTextChangedListener

class ProfileAttributeEditAdapter : RecyclerView.Adapter<ProfileAttributeEditAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, GenericDiffutilCallback<ProfileField>())
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
        private val title: TextView by lazy { view.findViewById<TextView>(R.id.profile_attribute_title) }
        private val value: TextView by lazy { view.findViewById<EditText>(R.id.profile_attribute_value) }

        fun bind(model: ProfileField) {
            title.text = view.context.getString(model.header)
            value.text = model.value
            value.addAfterTextChangedListener { editable ->
                model.value = editable.toString()
            }
        }
    }
}