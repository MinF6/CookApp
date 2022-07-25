package com.zongmin.viewpagercards


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.viewpager.widget.PagerAdapter
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.databinding.ItemRecipesViewPagerBinding
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.recipes.item.RecipesItemViewModel


class CardPagerAdapter(val viewModel: RecipesItemViewModel, private val recipesViewModel: RecipesViewModel, val onClickListener: OnClickListener) :
    PagerAdapter() {
    private val mViews: MutableList<CardView?>
    private var mData: MutableList<Recipes>
    var baseElevation = 0f
        private set

    fun addCardItem(item: Recipes) {
        item.let {
//            Log.d("hank1","進入addCardItem時機")
            mViews.add(null)
            mData.add(item)
        }

    }

    fun getCardViewAt(position: Int): CardView? {
        return mViews[position]
    }

    override fun getCount(): Int {
//        return mData.size
        return viewModel.recipes.value?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val view: View = LayoutInflater.from(container.context)
//            .inflate(R.layout.item_recipes_view_pager, container, false)
//        container.addView(view)
//        val layoutInflater = LayoutInflater.from(container.context)
        val binding = ItemRecipesViewPagerBinding.inflate(LayoutInflater.from(container.context), container, true)
        bind(mData[position], binding,position)
//        bind(mData[position], view,binding)


        return binding.root
    }

    fun submitList(data: List<Recipes>) {
        Log.d("hank1","進入submitList")
        this.mData = data as MutableList<Recipes>
        notifyDataSetChanged()
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        mViews[position] = null

    }

    //    private fun bind(item: CardItem, view: View, binding: ItemRecipesViewPagerBinding) {
    private fun bind(item: Recipes, binding: ItemRecipesViewPagerBinding, position: Int) {
//        val category = view.findViewById(R.id.text_recipes_item_category) as TextView
//        val title = view.findViewById(R.id.text_recipes_item_title) as TextView
//        val image = view.findViewById(R.id.image_recipes_item) as ImageView
        val category = binding.textRecipesItemCategory
        val title = binding.textRecipesItemTitle
        val image = binding.imageRecipesItem
        binding.textRecipesTime.text = "${item.cookingTime}分鐘"
        binding.textRecipesServing.text = "${item.serving}人份"



//        titleTextView.setText(viewModel.recipes.value?.get(0)?.name ?: "")
//        binding.titleTextView.setText("123456")
        val imgUrl = item.mainImage
//        image.setImageURI(Uri.parse("https://tokyo-kitchen.icook.network/uploads/recipe/cover/414397/a21961e2296a9aab.jpg"))

        //直接叫BindingAdapter的bindImage
        bindImage(image, imgUrl)
        category.setText(item.category)
        title.setText(item.name)

        val cardView = binding.cardView
        if (baseElevation == 0f) {
            baseElevation = cardView.cardElevation
        }
//        cardView.maxCardElevation = baseElevation * CardAdapter.MAX_ELEVATION_FACTOR
        cardView.maxCardElevation = baseElevation * 8
        mViews[position] = cardView

//        binding.root.setOnClickListener { onClickListener.onClick(item) }
        binding.root.setOnClickListener {
//            Log.d("hank1","點擊到這個item，這是 ->${item}")
            viewModel.navigateToDetail(item)
        }
//        cardView.setOnClickListener {
//            Log.d("hank1","點擊到這個item，這是 ->${item.title}")
//
//        }


        val btn = binding.buttonViewPagerAdd

        btn.setOnClickListener {
//            Log.d("hank1","點擊到這個item，這是 ->${item.name}")
//            Log.d("hank1","點擊到這個item，這是 ->${item.id}")
//            Log.d("hank1","點擊到這個item，他的食材是 ->${item.ingredient}")
//            Log.d("hank1","點擊到這個item，他隸屬於 ->${recipesViewModel.threeMeals.value}")
//            Log.d("hank1","點擊到這個item，他設定的日期為 ->${recipesViewModel.date.value}")
            val threeMeals = recipesViewModel.threeMeals.value!!
            val date = recipesViewModel.date.value!!
//            viewModel.setPlan(threeMeals,item.name,item.id,item.mainImage,item.category,date)
//            viewModel.planId.observe()
            for (management in item.ingredient){
//                Log.d("hank1","checkout 111")
//                viewModel.planId.value?.let { it1 ->
//                Log.d("hank1","checkout 222")
//                    viewModel.setManagement(item.name,management.ingredientName,
//                        it1,management.quantity,threeMeals,date,management.unit)
//                }
//            viewModel.setManagement(item.name,management.ingredientName,management.quantity,threeMeals,date,management.unit)

            }
            onClickListener.onClick(item)
        }

//        mData = ArrayList()
    }



    init {
        mData = ArrayList()
        mViews = ArrayList()
    }

    class OnClickListener(val clickListener: (recipes: Recipes) -> Unit) {
        fun onClick(recipes: Recipes) = clickListener(recipes)
    }

    fun remakeData(){
        mData = ArrayList()
//        Log.d("hank1","清空data -> $mData")
    }


}