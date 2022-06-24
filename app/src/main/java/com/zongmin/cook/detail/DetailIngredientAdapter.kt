package com.zongmin.cook.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Ingredient

import com.zongmin.cook.databinding.ItemIngredientBinding

class DetailIngredientAdapter : ListAdapter<Ingredient, RecyclerView.ViewHolder>(DiffCallback) {

    class DetailIngredientViewHolder(private var binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            binding.textIngredientName.text = ingredient.ingredientName
            binding.textIngredientQuantity.text = ingredient.quantity
            binding.textIngredientUnit.text = ingredient.unit

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DetailIngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemIngredientBinding.inflate(layoutInflater, parent, false)

                return DetailIngredientViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DetailIngredientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailIngredientViewHolder -> {
                val selectedItem = getItem(position) as Ingredient
                holder.bind(selectedItem)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem === newItem  //這邊是三個等號 判斷引用是否相等
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }


    }


}