package com.zongmin.cook.dialog

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DialogPlanAdapter:
    ListAdapter<HomeItem, RecyclerView.ViewHolder>(DiffCallback) {








    companion object DiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_TITLE = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL = 0x01
        private const val ITEM_VIEW_TYPE_PRODUCT_COLLAGE = 0x02
    }




}