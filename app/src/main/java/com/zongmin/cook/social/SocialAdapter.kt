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
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.databinding.ItemSocialBinding
import com.zongmin.cook.login.UserManager


class SocialAdapter(val onClickListener: OnClickListener, val viewModel: SocialViewModel) :
    ListAdapter<Recipes, RecyclerView.ViewHolder>(DiffCallback) {


    class SocialViewHolder(private var binding: ItemSocialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipes: Recipes, viewModel: SocialViewModel) {
            binding.recipes = recipes
            binding.viewModel = viewModel
            bindImage(
                binding.imageSocialUser,
                viewModel.userMap.value?.get(recipes.author)?.headShot
            )
//            Log.d("hank1","檢查想拿到的User是 -> ${recipes.author}")
//            Log.d("hank1","檢查想拿到的User大頭貼是 -> ${viewModel.userMap.value?.get(recipes.author)?.headShot}")
//            if()
            //收藏的判斷
//            binding.textSocialLike.text = (0..100).random().toString()
            binding.textSocialLike.text = recipes.like.size.toString()
            binding.textSocialName.text = recipes.name
            binding.textSocialUser.text = viewModel.userMap.value?.get(recipes.author)?.name

            if (UserManager.user.collect.contains(recipes.id)) {
                DrawableCompat.setTint(
                    binding.viewSocialCollect.background,
                    Color.rgb(255, 215, 0)
                )
//                var collect = binding.viewSocialCollect.background
//                collect = DrawableCompat.wrap(collect);
//                DrawableCompat.setTint(collect, Color.rgb(255, 215, 0));
//                binding.viewSocialCollect.background = collect;
            }

            if (recipes.like.contains(UserManager.user.id)) {
                DrawableCompat.setTint(binding.viewSocialLike.background, Color.rgb(255, 215, 0))

//                isLiked = true

//                var like = binding.viewSocialLike.background
//                like = DrawableCompat.wrap(like);
//                //the color is a direct color int and not a color resource
//                DrawableCompat.setTint(like, Color.rgb(255, 215, 0));
//                binding.viewSocialLike.background = like;

            }

            binding.viewSocialCollect.setOnClickListener {


                Log.d("hank1", "想收藏的這個是 -> $recipes")
                //viewModel那邊也判斷了，之後記得重構，在viewModel那邊判斷即可
                if (viewModel.changeCollect(recipes.id)) {
                    Log.d("hank1", "收藏，須改黃色")
                    DrawableCompat.setTint(
                        binding.viewSocialCollect.background,
                        Color.rgb(255, 215, 0)
                    )
                    viewModel.getUserResult()
                } else {
                    Log.d("hank1", "取消收藏，須改灰色")
                    DrawableCompat.setTint(
                        binding.viewSocialCollect.background,
                        Color.rgb(176, 176, 176)
                    )
//                    var collect = binding.viewSocialCollect.background
//                    collect = DrawableCompat.wrap(collect);
//                    DrawableCompat.setTint(collect, Color.rgb(176, 176, 176));
//                    binding.viewSocialCollect.background = collect;
                    viewModel.getUserResult()
                }

            }
            var isLiked = UserManager.user.collect.contains(recipes.id)
            binding.viewSocialAddLike.setOnClickListener {
                Log.d("hank1", "這個食譜原本有被點讚嗎? -> $isLiked")
                if (isLiked) {
                    //已經點讚要取消
                    isLiked = false
                    binding.textSocialLike.text =
                        ((binding.textSocialLike.text as String).toInt() - 1).toString()
                    DrawableCompat.setTint(
                        binding.viewSocialLike.background,
                        Color.rgb(176, 176, 176)
                    )
                    viewModel.changeLike(recipes.id, true)

                } else {
                    //點擊喜歡
                    isLiked = true
                    binding.textSocialLike.text =
                        ((binding.textSocialLike.text as String).toInt() + 1).toString()
                    DrawableCompat.setTint(
                        binding.viewSocialLike.background,
                        Color.rgb(255, 215, 0)
                    )
                    viewModel.changeLike(recipes.id, false)

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
                val selectedItem = getItem(position) as Recipes
                holder.bind(selectedItem, viewModel)
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