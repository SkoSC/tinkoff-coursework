package com.skosc.tkffintech.utils

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtilCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem === newItem
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}