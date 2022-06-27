package com.zongmin.cook.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.databinding.ItemProfileCreationBinding

class ProfileAdapter : ListAdapter<Recipes, RecyclerView.ViewHolder>(DiffCallback) {

    class UserViewHolder(private var binding: ItemProfileCreationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipes: Recipes) {
            binding.result = recipes
            binding.textProfileRecipes.text = recipes.name




            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProfileCreationBinding.inflate(layoutInflater, parent, false)

                return UserViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
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