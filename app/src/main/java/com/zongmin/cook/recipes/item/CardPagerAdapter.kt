package com.zongmin.viewpagercards


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.zongmin.cook.R
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.recipes.item.CardItem
import com.zongmin.cook.recipes.item.RecipesItemViewModel
import java.io.InputStream
import java.net.URL


class CardPagerAdapter(val viewModel: RecipesItemViewModel, val onClickListener: OnClickListener) :
    PagerAdapter() {
    private val mViews: MutableList<CardView?>
    private val mData: MutableList<CardItem>
    var baseElevation = 0f
        private set

    fun addCardItem(item: CardItem) {
        mViews.add(null)
        mData.add(item)
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
        val view: View = LayoutInflater.from(container.context)
            .inflate(R.layout.item_recipes_view_pager, container, false)
        container.addView(view)
//        val layoutInflater = LayoutInflater.from(container.context)
//        val binding = ItemRecipesViewPagerBinding.inflate(layoutInflater, container, false)
//        val binding = ItemRecipesViewPagerBinding.inflate(LayoutInflater.from(container.context), container, false)
        bind(mData[position], view)
//        bind(mData[position], view,binding)
        val cardView = view.findViewById(R.id.cardView) as CardView
        if (baseElevation == 0f) {
            baseElevation = cardView.cardElevation
        }
//        cardView.maxCardElevation = baseElevation * CardAdapter.MAX_ELEVATION_FACTOR
        cardView.maxCardElevation = baseElevation * 8
        mViews[position] = cardView

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        mViews[position] = null

    }

    //    private fun bind(item: CardItem, view: View, binding: ItemRecipesViewPagerBinding) {
    private fun bind(item: CardItem, view: View) {
        val category = view.findViewById(R.id.text_recipes_item_category) as TextView
        val title = view.findViewById(R.id.text_recipes_item_title) as TextView
        val image = view.findViewById(R.id.image_recipes_item) as ImageView

//        titleTextView.setText(viewModel.recipes.value?.get(0)?.name ?: "")
//        binding.titleTextView.setText("123456")
        val imgUrl = item.image
//        image.setImageURI(Uri.parse("https://tokyo-kitchen.icook.network/uploads/recipe/cover/414397/a21961e2296a9aab.jpg"))

        //直接叫BindingAdapter的bindImage
        bindImage(image, imgUrl)
        category.setText(item.category)
        title.setText(item.title)

        val btn = view.findViewById(R.id.button_view_pager_add) as Button
        btn.setOnClickListener {
            Log.d("hank1","點擊到按鈕了，這是 ->${item.title}")
        }

//        val drawable: Drawable =
//            LoadImageFromWebOperations("https://tokyo-kitchen.icook.network/uploads/recipe/cover/414397/a21961e2296a9aab.jpg")
//        image.setImageDrawable(drawable)
//        Log.d("hank1", "看看有沒有取得viewModel的recipes資料 -> ${viewModel.recipes.value} ")

    }

    private fun LoadImageFromWebOperations(url: String): Drawable {
        return try {
            val `is`: InputStream = URL(url).getContent() as InputStream
            Drawable.createFromStream(`is`, "src name")
        } catch (e: Exception) {
            val `is`: InputStream = URL(url).getContent() as InputStream
            Drawable.createFromStream(`is`, "src name")
        }
    }

//    fun submitList(color: List<String>) {
//        this.color = color
//        notifyDataSetChanged()
//    }

    init {
        mData = ArrayList()
        mViews = ArrayList()
    }

    class OnClickListener(val clickListener: (recipes: Recipes) -> Unit) {
        fun onClick(recipes: Recipes) = clickListener(recipes)
    }


}