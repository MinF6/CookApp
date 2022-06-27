package com.zongmin.cook.plan

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.PlanContent
import com.zongmin.cook.databinding.ItemPlanBinding
import com.zongmin.cook.databinding.ItemPlanThreeMealsBinding


class PlanAdapter : ListAdapter<PlanItem , RecyclerView.ViewHolder>(DiffCallback) {


    class TitleViewHolder(private var binding: ItemPlanThreeMealsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.textPlanThreeMeals.text = title

            binding.executePendingBindings()
        }
    }

    class PlanContentViewHolder(private var binding: ItemPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(planContent: PlanContent) {
            binding.planContent = planContent
            binding.textPlanName.text = planContent.name
            binding.textPlanCategory.text = planContent.category

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_TITLE -> TitleViewHolder(
                ItemPlanThreeMealsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_PRODUCT_FULL -> PlanContentViewHolder(
                ItemPlanBinding.inflate(
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
            is TitleViewHolder -> {
                holder.bind((getItem(position) as PlanItem.Title).title)
            }
            is PlanContentViewHolder -> {
                holder.bind((getItem(position) as PlanItem.FullPlan).planContent)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PlanItem.Title -> ITEM_VIEW_TYPE_TITLE
            is PlanItem.FullPlan -> ITEM_VIEW_TYPE_PRODUCT_FULL
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<PlanItem>() {
        override fun areItemsTheSame(oldItem: PlanItem, newItem: PlanItem): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: PlanItem, newItem: PlanItem): Boolean {
            return oldItem == newItem
        }

        private const val ITEM_VIEW_TYPE_TITLE = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL = 0x01
        private const val ITEM_VIEW_TYPE_PRODUCT_COLLAGE = 0x02
    }






}