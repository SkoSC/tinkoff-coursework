package com.skosc.tkffintech.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class GenericRecyclerAdapter<T, VH : BindableViewHolder<T>>(
        diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

    @Suppress("LeakingThis")
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitItems(items: List<T>) {
        differ.submitList(items)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

}