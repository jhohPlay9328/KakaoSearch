package com.jhoh.play.app.common.adapter

import android.view.ViewGroup
import com.jhoh.play.app.BaseDiffListAdapter
import com.jhoh.play.app.BaseViewHolder
import com.jhoh.play.app.common.listener.InteractionListener
import com.jhoh.play.app.ui.favorite.FavoriteHolder


class RecyclerDiffAdapter(
    private val interactionListener: InteractionListener? = null,
): BaseDiffListAdapter<Any>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        return FavoriteHolder(parent, interactionListener)
    }
}