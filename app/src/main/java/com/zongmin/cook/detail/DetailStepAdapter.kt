package com.zongmin.cook.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Step
import com.zongmin.cook.databinding.ItemStepBinding

class DetailStepAdapter : ListAdapter<Step, RecyclerView.ViewHolder>(DiffCallback) {

    class DetailStepViewHolder(private var binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: Step) {
            binding.step = step
            binding.textStepSequence.text = "步驟 ${step.sequence}"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DetailStepViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStepBinding.inflate(layoutInflater, parent, false)
                return DetailStepViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DetailStepViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailStepViewHolder -> {
                val selectedItem = getItem(position) as Step
                holder.bind(selectedItem)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem == newItem
        }
    }

}