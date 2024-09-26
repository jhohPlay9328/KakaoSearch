package com.jhoh.play.app.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jhoh.play.app.BaseViewHolder
import com.jhoh.play.app.databinding.ItemMediaBinding
import com.jhoh.play.app.common.listener.InteractionListener
import com.jhoh.play.domain.model.MediaModel

class MediaHolder<ITEM: Any>(
    private val parent: ViewGroup,
    private val interactionListener: InteractionListener? = null,
    private val binding: ItemMediaBinding = ItemMediaBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ),
): BaseViewHolder<ITEM>(binding.root){
    override fun bind(item: ITEM, position: Int) {
        binding.item = item as MediaModel.Document
        binding.interactionListener = interactionListener
    }
}


