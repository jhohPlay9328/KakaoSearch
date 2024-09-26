package com.jhoh.play.app.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jhoh.play.app.BaseViewHolder
import com.jhoh.play.app.databinding.ItemPageBinding

class PageHolder<ITEM: Any>(
    private val parent: ViewGroup,
    private val binding: ItemPageBinding = ItemPageBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ),
): BaseViewHolder<ITEM>(binding.root){
    override fun bind(item: ITEM, position: Int) {
        binding.item = item.toString()
    }
}


