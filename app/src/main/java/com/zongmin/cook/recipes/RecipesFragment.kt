package com.zongmin.cook.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentRecipesBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.recipes.item.RecipesItemViewModel
import com.zongmin.viewpagercards.CardPagerAdapter
import com.zongmin.viewpagercards.ShadowTransformer


class RecipesFragment : Fragment() {
    private var mViewPager: ViewPager? = null
    private var mCardAdapter: CardPagerAdapter? = null
    private var mCardShadowTransformer: ShadowTransformer? = null
    private val viewModel by viewModels<RecipesItemViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesBinding.inflate(inflater, container, false)
        mViewPager = binding.viewPager
        mCardAdapter = CardPagerAdapter()
        mCardAdapter!!.addCardItem(CardItem(1, 1))
        mCardAdapter!!.addCardItem(CardItem(2, 2))
        mCardAdapter!!.addCardItem(CardItem(3, 3))
        mCardAdapter!!.addCardItem(CardItem(4, 4))
        mCardShadowTransformer = ShadowTransformer(mViewPager!!, mCardAdapter!!)
        mCardShadowTransformer!!.enableScaling(true)
        mViewPager!!.adapter = mCardAdapter
        mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
        mViewPager!!.offscreenPageLimit = 3

        viewModel.getRecipesResult()

        return binding.root

//        FragmentRecipesBinding.inflate(inflater, container, false).apply {
//            lifecycleOwner = viewLifecycleOwner
//            viewpagerRecipes.let {
//                tabsRecipes.setupWithViewPager(it)
//                it.adapter = RecipesAdapter(childFragmentManager)
//                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsRecipes))
//            }
//
//            buttonNavNew.setOnClickListener {
////                this.findNavController().navigate(MainNavigationDirections.navigateToArticleFragment())
//            findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment())
//            }
//
//            return@onCreateView root
//        }

    }


}