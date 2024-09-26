package com.jhoh.play.app.common.adapter

import android.view.ViewGroup
import com.jhoh.play.app.BaseListAdapter
import com.jhoh.play.app.BaseViewHolder
import com.theenm.android.popcaster.common.constants.BaseConst
import com.jhoh.play.app.common.listener.InteractionListener
import com.jhoh.play.app.ui.favorite.FavoriteHolder
import com.jhoh.play.app.ui.search.MediaHolder
import com.jhoh.play.app.ui.search.PageHolder


class RecyclerAdapter(
    private val interactionListener: InteractionListener? = null,
): BaseListAdapter<Any>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        return when(viewType) {
            BaseConst.ViewType.MEDIA -> MediaHolder(parent, interactionListener)
            BaseConst.ViewType.PAGE_NUM -> PageHolder(parent)
            BaseConst.ViewType.FAVORITE -> FavoriteHolder(parent, interactionListener)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
}