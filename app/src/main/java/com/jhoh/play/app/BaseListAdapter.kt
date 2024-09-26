package com.jhoh.play.app

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("NotifyDataSetChanged")
abstract class BaseListAdapter<ITEM: Any>: RecyclerView.Adapter<BaseViewHolder<ITEM>>() {
    init {
        this.setHasStableIds(true)
    }

    private var items: MutableList<BaseRow<*>> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return items[position].getItemViewType()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getRows() = items

    fun getItem(position: Int) = items[position].getItem()


    fun itemInsert(position: Int, item: BaseRow<*>) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun itemInsertLast(item: BaseRow<*>) {
        this.items += item
        notifyItemInserted(items.size)
    }

    fun itemInsertLast(items: List<BaseRow<*>>) {
        this.items += items
        notifyItemInserted(this.items.size)
    }

    fun itemInsertRange(items: List<BaseRow<*>>) {
        this.items += items
        notifyItemRangeInserted(this.items.size - items.size, items.size)
    }

    fun itemChange(position: Int, item: BaseRow<*>) {
        items[position] = item

        notifyItemChanged(position)
    }

    fun itemChange(position: Int) {
        notifyItemChanged(position)
    }

    fun itemRemove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ITEM>, position: Int) {
        @Suppress("UNCHECKED_CAST")
        holder.bind(items[position].getItem() as ITEM, position)
    }
}