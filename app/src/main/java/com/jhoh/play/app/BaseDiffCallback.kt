package com.jhoh.play.app

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback<T : Any> : DiffUtil.ItemCallback<BaseRow<T>>() {
    override fun areItemsTheSame(oldItem: BaseRow<T>, newItem: BaseRow<T>): Boolean {
        return oldItem.getItem().toString() == newItem.getItem().toString()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseRow<T>, newItem: BaseRow<T>): Boolean {
        return oldItem.getItem() == newItem.getItem()
    }
}