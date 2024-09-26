package com.jhoh.play.app

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<ITEM: Any>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ITEM, position: Int)
}