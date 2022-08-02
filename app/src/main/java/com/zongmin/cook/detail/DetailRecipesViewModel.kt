package com.zongmin.cook.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipe
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.Step
import com.zongmin.cook.data.source.CookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailRecipesViewModel(
    private val cookRepository: CookRepository
)  : ViewModel() {

    private var _ingredients = MutableLiveData<List<Ingredient>>()

    val ingredients: LiveData<List<Ingredient>>
        get() = _ingredients


    private var _steps = MutableLiveData<List<Step>>()

    val steps: LiveData<List<Step>>
        get() = _steps

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getIngredients(recipe: Recipe) {
        _ingredients.value = recipe.ingredient
    }

    fun getSteps(recipe: Recipe) {
        _steps.value = recipe.step
    }

    fun setPublicRecipes(isPublic: Boolean,recipesId: String) {
        coroutineScope.launch {
            when (val result = cookRepository.setPublic(isPublic, recipesId)) {
                is Result.Success -> {
                    result.data
                }
                is Result.Fail -> {
                    null
                }
                is Result.Error -> {
                    null
                }
                else -> {
                    null
                }
            }
        }
    }


}