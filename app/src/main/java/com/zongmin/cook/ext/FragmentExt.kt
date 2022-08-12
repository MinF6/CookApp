package com.zongmin.cook.ext

import androidx.fragment.app.Fragment
import com.zongmin.cook.CookApplication
import com.zongmin.cook.factory.RecipesItemViewModelFactory
import com.zongmin.cook.factory.ViewModelFactory
import com.zongmin.cook.recipes.RecipesTypeFilter

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as CookApplication).cookRepository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(recipesType: RecipesTypeFilter): RecipesItemViewModelFactory {
    val repository = (requireContext().applicationContext as CookApplication).cookRepository
    return RecipesItemViewModelFactory(repository, recipesType)
}
