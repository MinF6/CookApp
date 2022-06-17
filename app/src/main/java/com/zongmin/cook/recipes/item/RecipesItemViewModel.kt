package com.zongmin.cook.recipes.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.recipes.RecipesTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class RecipesItemViewModel(
    private val cookRepository: CookRepository,
    recipesType: RecipesTypeFilter // Handle the type for each catalog item
) : ViewModel() {


}
