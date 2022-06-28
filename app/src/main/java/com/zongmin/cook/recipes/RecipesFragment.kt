package com.zongmin.cook.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentRecipesBinding
import java.text.SimpleDateFormat
import java.util.*


class RecipesFragment : Fragment() {

    //    private var mViewPager: ViewPager? = null
//
//    private var mCardAdapter: CardPagerAdapter? = null
//    private var mCardShadowTransformer: ShadowTransformer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val binding = FragmentRecipesBinding.inflate(inflater, container, false)


//        mViewPager = binding.viewpagerRecipes
//
//        mCardAdapter = CardPagerAdapter()
//        mCardAdapter.addCardItem(CardItem(1,1))
//        mCardAdapter.addCardItem(CardItem(2, 2))
//        mCardAdapter.addCardItem(CardItem(3, 3))
//        mCardAdapter.addCardItem(CardItem(4, 4))
//
//        mCardShadowTransformer = ShadowTransformer(mViewPager, mCardAdapter)
//        mCardShadowTransformer.enableScaling(true)
//
//        mViewPager.setAdapter(mCardAdapter)
//        mViewPager.setPageTransformer(false, mCardShadowTransformer)
//        mViewPager.setOffscreenPageLimit(3)


//        return binding.root

        FragmentRecipesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner

            viewpagerRecipes.let {
                tabsRecipes.setupWithViewPager(it)
                tabsRecipes.tabMode = TabLayout.MODE_SCROLLABLE;
//                val adapter = RecipesAdapter(childFragmentManager)
                it.adapter = RecipesAdapter(childFragmentManager)
//                it.adapter = adapter
                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsRecipes))
            }

            buttonRecipesDialog.text = SimpleDateFormat("MM/dd").format(Date())

            //新增食譜
            buttonNavNew.setOnClickListener {
//                this.findNavController().navigate(MainNavigationDirections.navigateToArticleFragment())
                findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment())
            }

            buttonReicpesSearch.setOnClickListener {
//                RecipesItemFragment
            }


            buttonRecipesDialog.setOnClickListener {
                findNavController().navigate(NavigationDirections.navigateToDialogPlan())

            }

            return@onCreateView root
        }

    }


}