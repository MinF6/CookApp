package com.zongmin.cook.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Recipe
import com.zongmin.cook.databinding.ItemProfileCreationBinding

class ProfileAdapter(val onClickListener: OnClickListener) : ListAdapter<Recipe, RecyclerView.ViewHolder>(DiffCallback) {

    class UserViewHolder(private var binding: ItemProfileCreationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            binding.textProfileRecipes.text = recipe.name
            var ingredientlist = ""
            for(i in recipe.ingredient){
                ingredientlist += "${i.ingredientName}\n"
            }
            binding.textProfileIngredient.text = ingredientlist




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
                val selectedItem = getItem(position) as Recipe
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(getItem(position))
                }
                holder.bind(selectedItem)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }


    }

    class OnClickListener(val clickListener: (recipe: Recipe) -> Unit) {
        fun onClick(recipe: Recipe) = clickListener(recipe)
    }

}