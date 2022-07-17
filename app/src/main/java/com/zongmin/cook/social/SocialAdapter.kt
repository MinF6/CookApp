package com.zongmin.cook.social

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.databinding.ItemSocialBinding


class SocialAdapter(val onClickListener: OnClickListener, val viewModel: SocialViewModel) : ListAdapter<Recipes, RecyclerView.ViewHolder>(DiffCallback) {


    class SocialViewHolder(private var binding: ItemSocialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipes: Recipes, viewModel: SocialViewModel) {
            binding.recipes = recipes
//            binding.viewModel = viewModel
            bindImage(binding.imageSocialUser, viewModel.userMap.value?.get(recipes.author)?.headShot)
//            Log.d("hank1","檢查想拿到的User是 -> ${recipes.author}")
//            Log.d("hank1","檢查想拿到的User大頭貼是 -> ${viewModel.userMap.value?.get(recipes.author)?.headShot}")
//            if()
            //收藏的判斷

            binding.textSocialName.text = recipes.name
            binding.textSocialUser.text = viewModel.userMap.value?.get(recipes.author)?.name
            binding.viewSocialCollect.setOnClickListener {
                Log.d("hank1","想收藏的這個是 -> $recipes")
            }


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
                holder.bind(selectedItem,viewModel)
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(getItem(position))
                }
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

    class OnClickListener(val clickListener: (recipes: Recipes) -> Unit) {
        fun onClick(recipes: Recipes) = clickListener(recipes)
    }


}