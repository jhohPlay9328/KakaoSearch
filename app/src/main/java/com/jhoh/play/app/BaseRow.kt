package com.jhoh.play.app

class BaseRow<ITEM: Any>(
    private val item: ITEM?,
    private val itemViewType: Int
) {
    fun getItem(): ITEM? {
        return item
    }

    fun getItemViewType(): Int {
        return itemViewType
    }
}