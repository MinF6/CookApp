package com.zongmin.cook.recipes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.zongmin.cook.recipes.item.RecipesItemFragment

class RecipesAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return RecipesItemFragment(RecipesTypeFilter.values()[position])
    }

    override fun getCount() = RecipesTypeFilter.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return RecipesTypeFilter.values()[position].value
    }
}