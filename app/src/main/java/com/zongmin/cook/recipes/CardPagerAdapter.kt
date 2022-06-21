package com.zongmin.viewpagercards


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.zongmin.cook.R
import com.zongmin.cook.recipes.CardItem


//abstract class CardPagerAdapter : PagerAdapter(), CardAdapter {
class CardPagerAdapter : PagerAdapter() {
//class CardPagerAdapter : CardAdapter {
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
        return mData.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context)
            .inflate(R.layout.item_recipes_view_pager, container, false)
        container.addView(view)
        bind(mData[position], view)
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

    private fun bind(item: CardItem, view: View) {
//        val binding = ItemGridCatalogBinding.inflate(layoutInflater, parent, false)
        val titleTextView = view.findViewById(R.id.titleTextView) as TextView
        val contentTextView = view.findViewById(R.id.contentTextView) as TextView
        titleTextView.setText(item.title.toString())
        contentTextView.setText(item.text.toString())
    }

    init {
        mData = ArrayList()
        mViews = ArrayList()
    }
}