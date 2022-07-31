package com.zongmin.cook.social

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.Recipe
import com.zongmin.cook.databinding.ItemSocialBinding
import com.zongmin.cook.login.UserManager


class SocialAdapter(private val onClickListener: OnClickListener, val viewModel: SocialViewModel) :
    ListAdapter<Recipe, RecyclerView.ViewHolder>(DiffCallback) {


    class SocialViewHolder(private var binding: ItemSocialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe, viewModel: SocialViewModel) {
            binding.recipe = recipe
            binding.viewModel = viewModel
            bindImage(
                binding.imageSocialUser,
                viewModel.userMap.value?.get(recipe.author)?.headShot
            )

            binding.textSocialLike.text = recipe.like.size.toString()
            binding.textSocialName.text = recipe.name
            binding.textSocialUser.text = viewModel.userMap.value?.get(recipe.author)?.name

            if (UserManager.user.collect.contains(recipe.id)) {
                DrawableCompat.setTint(
                    binding.viewSocialCollect.background,
                    Color.rgb(255, 215, 0)
                )
            }

            if (recipe.like.contains(UserManager.user.id)) {
                DrawableCompat.setTint(binding.viewSocialLike.background, Color.rgb(255, 215, 0))

            }

            binding.viewSocialCollect.setOnClickListener {

                if (viewModel.changeCollect(recipe.id)) {
                    DrawableCompat.setTint(
                        binding.viewSocialCollect.background,
                        Color.rgb(255, 215, 0)
                    )
                    viewModel.getUserResult()
                } else {
                    DrawableCompat.setTint(
                        binding.viewSocialCollect.background,
                        Color.rgb(176, 176, 176)
                    )
                    viewModel.getUserResult()
                }

            }
            var isLiked = UserManager.user.collect.contains(recipe.id)
            binding.viewSocialAddLike.setOnClickListener {
                if (isLiked) {
                    isLiked = false
                    binding.textSocialLike.text =
                        ((binding.textSocialLike.text as String).toInt() - 1).toString()
                    DrawableCompat.setTint(
                        binding.viewSocialLike.background,
                        Color.rgb(176, 176, 176)
                    )
                    viewModel.changeLike(recipe.id, true)

                } else {
                    isLiked = true
                    binding.textSocialLike.text =
                        ((binding.textSocialLike.text as String).toInt() + 1).toString()
                    DrawableCompat.setTint(
                        binding.viewSocialLike.background,
                        Color.rgb(255, 215, 0)
                    )
                    viewModel.changeLike(recipe.id, false)

                }

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
                val selectedItem = getItem(position) as Recipe
                holder.bind(selectedItem, viewModel)
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(getItem(position))
                }
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