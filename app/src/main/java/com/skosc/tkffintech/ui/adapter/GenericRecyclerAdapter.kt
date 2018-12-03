package com.skosc.tkffintech.ui.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skosc.tkffintech.utils.GenericDiffutilCallback

abstract class GenericRecyclerAdapter<T, VH : BindableViewHolder<T>>(
        diffCallback: DiffUtil.ItemCallback<T> = GenericDiffutilCallback()
) : RecyclerView.Adapter<VH>() {

    protected abstract fun getItemId(item: T): Long

    init {
        setHasStableIds(true)
    }

    @Suppress("LeakingThis")
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitItems(items: List<T>) {
        differ.submitList(items)
    }

    override fun getItemId(position: Int): Long {
        val item = differ.currentList[position]
        return getItemId(item)
    }

    final override fun getItemCount(): Int = differ.currentList.size

    final override fun onBindViewHolder(holder: VH, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    final override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }
}