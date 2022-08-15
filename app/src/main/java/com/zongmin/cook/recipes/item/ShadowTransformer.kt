package com.zongmin.viewpagercards

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener


class ShadowTransformer(private val viewPager: ViewPager, adapter: CardPagerAdapter) :
    OnPageChangeListener, ViewPager.PageTransformer {
    private val adapter: CardPagerAdapter

    //    private val mAdapter: CardAdapter
    private var lastOffset = 0f
    private var scalingEnabled = false
    fun enableScaling(enable: Boolean) {
        if (scalingEnabled && !enable) {
            val currentCard = adapter.getCardViewAt(viewPager.currentItem)
            if (currentCard != null) {
                currentCard.animate().scaleY(1f)
                currentCard.animate().scaleX(1f)
            }
        } else if (!scalingEnabled && enable) {
            val currentCard = adapter.getCardViewAt(viewPager.currentItem)
            if (currentCard != null) {
                currentCard.animate().scaleY(1.1f)
                currentCard.animate().scaleX(1.1f)
            }
        }
        scalingEnabled = enable
    }

    override fun transformPage(page: View, position: Float) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val realCurrentPosition: Int
        val nextPosition: Int
        val baseElevation = adapter.baseElevation
        val realOffset: Float
        val goingLeft = lastOffset > positionOffset

        if (goingLeft) {
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        } else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }

        if (nextPosition > adapter.count - 1
            || realCurrentPosition > adapter.count - 1
        ) {
            return
        }
        val currentCard = adapter.getCardViewAt(realCurrentPosition)

        if (currentCard != null) {
            if (scalingEnabled) {
                currentCard.scaleX = (1 + 0.1 * (1 - realOffset)).toFloat()
                currentCard.scaleY = (1 + 0.1 * (1 - realOffset)).toFloat()
            }
            currentCard.cardElevation = baseElevation + (baseElevation
                    * (8 - 1) * (1 - realOffset))
        }
        val nextCard = adapter.getCardViewAt(nextPosition)

        if (nextCard != null) {
            if (scalingEnabled) {
                nextCard.scaleX = (1 + 0.1 * realOffset).toFloat()
                nextCard.scaleY = (1 + 0.1 * realOffset).toFloat()
            }
            nextCard.cardElevation = baseElevation + (baseElevation
                    * (8 - 1) * realOffset)
        }
        lastOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}

    init {
        viewPager.addOnPageChangeListener(this)
        this.adapter = adapter
    }
}