package com.skosc.tkffintech.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.skosc.tkffintech.misc.ProfileField

object ProfileFieldAttributeDiffCallback : DiffUtil.ItemCallback<ProfileField>() {
    override fun areItemsTheSame(oldItem: ProfileField, newItem: ProfileField): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProfileField, newItem: ProfileField): Boolean {
        return oldItem == newItem
    }

}