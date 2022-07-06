package com.zongmin.cook.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.Step
import com.zongmin.cook.databinding.ItemStepBinding

class DetailStepAdapter: ListAdapter<Step, RecyclerView.ViewHolder>(DiffCallback) {

    class DetailStepViewHolder(private var binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: Step) {

            binding.textStepSequence.text = step.sequence
            binding.textStepDepiction.text = step.depiction

            //這裡是listString，之後要多改個recycle
            bindImage(binding.imageStep, step.images)


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
        when (holder){
            is DetailStepViewHolder ->{
                val selectedItem = getItem(position) as Step
                holder.bind(selectedItem)
            }
        }
    }











    companion object DiffCallback : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem === newItem  //這邊是三個等號 判斷引用是否相等
        }

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem == newItem
        }


    }




}