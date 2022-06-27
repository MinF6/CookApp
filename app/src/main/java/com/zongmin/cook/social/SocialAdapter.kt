package com.zongmin.cook.social

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.databinding.ItemSocialBinding


class SocialAdapter : ListAdapter<Recipes, RecyclerView.ViewHolder>(DiffCallback) {


    class SocialViewHolder(private var binding: ItemSocialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipes: Recipes) {
            binding.recipes = recipes
            binding.textSocialName.text = recipes.name
            binding.textSocialUser.text = "Hank"


            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SocialViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSocialBinding.inflate(layoutInflater, parent, false)

                return SocialViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SocialViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SocialViewHolder -> {
                val selectedItem = getItem(position) as Recipes
                holder.bind(selectedItem)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Recipes>() {
        override fun areItemsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
            return oldItem == newItem
        }


    }


}