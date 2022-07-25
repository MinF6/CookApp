package com.zongmin.cook.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipes
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

    private var _ingredient = MutableLiveData<List<Ingredient>>()

    val ingredient: LiveData<List<Ingredient>>
        get() = _ingredient


    private var _stepData = MutableLiveData<List<Step>>()

    val stepData: LiveData<List<Step>>
        get() = _stepData

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getIngredient(recipes: Recipes) {
        _ingredient.value = recipes.ingredient
        Log.d("hank1", "這欄的size是 ${recipes.ingredient.size}")

    }

    fun getStep(recipes: Recipes) {
        _stepData.value = recipes.step
        Log.d("hank1", "這欄的size是 ${recipes.step.size}")

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