package com.zongmin.cook.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.recipes.RecipesTypeFilter
import com.zongmin.cook.recipes.item.RecipesItemViewModel


@Suppress("UNCHECKED_CAST")
class RecipesItemViewModelFactory(
    private val cookRepository: CookRepository,
//    private val recipesType: RecipesTypeFilter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(RecipesItemViewModel::class.java) ->
//                    RecipesItemViewModel(cookRepository, recipesType)
                    RecipesItemViewModel(cookRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
