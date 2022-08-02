package com.zongmin.cook.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.PlanContent
import com.zongmin.cook.databinding.ItemDialogBinding
import com.zongmin.cook.databinding.ItemDialogThreeMealsBinding

class DialogPlanAdapter(val viewModel: DialogPlanViewModel) :
    ListAdapter<DialogItem, RecyclerView.ViewHolder>(DiffCallback) {


    class DialogTitleViewHolder(private var binding: ItemDialogThreeMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.textDialogThree.text = title
            binding.executePendingBindings()
        }
    }

    class DialogPlanContentViewHolder(private var binding: ItemDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan, viewModel: DialogPlanViewModel) {
            binding.textDialogTitle.text = plan.planContent.name
            binding.textDialogCategory.text = plan.planContent.category
            binding.buttonDialogCancel.setOnClickListener {
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_TITLE -> DialogTitleViewHolder(
                ItemDialogThreeMealsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_PRODUCT_FULL -> DialogPlanContentViewHolder(
                ItemDialogBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is DialogTitleViewHolder -> {
                holder.bind((getItem(position) as DialogItem.Title).title)
            }
            is DialogPlanContentViewHolder -> {
                holder.bind((getItem(position) as DialogItem.FullPlan).plan, viewModel)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DialogItem.Title -> ITEM_VIEW_TYPE_TITLE
            is DialogItem.FullPlan -> ITEM_VIEW_TYPE_PRODUCT_FULL
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<DialogItem>() {
        override fun areItemsTheSame(oldItem: DialogItem, newItem: DialogItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DialogItem, newItem: DialogItem): Boolean {
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_TITLE = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL = 0x01
    }


}