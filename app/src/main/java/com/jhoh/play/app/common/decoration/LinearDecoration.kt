package com.theenm.popkontv.presentation.common.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LinearDecoration(
    private val topPadding: Float = 0f,
    private val bottomPadding: Float = 0f,
): RecyclerView.ItemDecoration()  {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if(parent.layoutManager is LinearLayoutManager) {
            when((parent.layoutManager as LinearLayoutManager).orientation) {
                RecyclerView.VERTICAL -> {
                    outRect.top = topPadding.toInt()
                    outRect.bottom = bottomPadding.toInt()
                }
                RecyclerView.HORIZONTAL -> {
                    outRect.left = topPadding.toInt()
                    outRect.right = bottomPadding.toInt()
                }
            }
        }
    }
}