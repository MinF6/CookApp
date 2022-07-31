package com.zongmin.viewpagercards


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.Recipe
import com.zongmin.cook.databinding.ItemRecipesViewPagerBinding
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.recipes.item.RecipesItemViewModel


class CardPagerAdapter(
    val viewModel: RecipesItemViewModel,
    private val recipesViewModel: RecipesViewModel,
    val onClickListener: OnClickListener
) :
    PagerAdapter() {
    private val mViews: MutableList<CardView?>
    private var mData: MutableList<Recipe>
    var baseElevation = 0f
        private set

    fun addCardItem(item: Recipe) {
        item.let {
            mViews.add(null)
            mData.add(item)
        }
    }

    fun getCardViewAt(position: Int): CardView? {
        return mViews[position]
    }

    override fun getCount(): Int {
        return viewModel.recipe.value?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemRecipesViewPagerBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        bind(mData[position], binding, position)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        mViews[position] = null

    }

    private fun bind(item: Recipe, binding: ItemRecipesViewPagerBinding, position: Int) {

        val category = binding.textRecipesItemCategory
        val title = binding.textRecipesItemTitle
        val image = binding.imageRecipesItem
        binding.textRecipesTime.text = "${item.cookingTime}分鐘"
        binding.textRecipesServing.text = "${item.serving}人份"

        val imgUrl = item.mainImage

        bindImage(image, imgUrl)
        category.setText(item.category)
        title.setText(item.name)

        val cardView = binding.cardView
        if (baseElevation == 0f) {
            baseElevation = cardView.cardElevation
        }

        cardView.maxCardElevation = baseElevation * 8
        mViews[position] = cardView

        binding.root.setOnClickListener {
            viewModel.navigateToDetail(item)
        }


        val btn = binding.buttonViewPagerAdd

        btn.setOnClickListener {
            val threeMeals = recipesViewModel.threeMeals.value!!
            val date = recipesViewModel.date.value!!
//            viewModel.setPlan(threeMeals,item.name,item.id,item.mainImage,item.category,date)
            for (management in item.ingredient) {

            }
            onClickListener.onClick(item)
        }

    }


    init {
        mData = ArrayList()
        mViews = ArrayList()
    }

    class OnClickListener(val clickListener: (recipe: Recipe) -> Unit) {
        fun onClick(recipe: Recipe) = clickListener(recipe)
    }

    fun remakeData() {
        mData = ArrayList()

    }

}