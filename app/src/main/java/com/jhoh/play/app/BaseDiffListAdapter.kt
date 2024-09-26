package com.jhoh.play.app

import androidx.recyclerview.widget.ListAdapter

abstract class BaseDiffListAdapter<ITEM: Any>: ListAdapter<BaseRow<ITEM>, BaseViewHolder<ITEM>>(
    BaseDiffCallback()
) {
    override fun onBindViewHolder(holder: BaseViewHolder<ITEM>, position: Int) {
        holder.bind(getItem(position).getItem() as ITEM, position)
    }
}