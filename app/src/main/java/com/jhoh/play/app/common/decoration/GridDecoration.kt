package com.theenm.popkontv.presentation.common.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GridDecoration(
    private val verticalPadding: Float = 0f,
    private val horizontalPadding: Float = 0f,
): RecyclerView.ItemDecoration()  {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if(parent.layoutManager is GridLayoutManager) {
            // 그리드 아이템이 3개 이상인 경우의 확장성을 고려하여 아래 방법으로 설정
            outRect.left = verticalPadding.toInt() / 2
            outRect.right = verticalPadding.toInt() / 2
            outRect.bottom =  horizontalPadding.toInt()
        }
    }
}