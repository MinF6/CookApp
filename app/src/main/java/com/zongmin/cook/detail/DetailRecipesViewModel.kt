package com.zongmin.cook.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Step

class DetailRecipesViewModel: ViewModel() {

    var _ingredient = MutableLiveData<List<Ingredient>>()

    val ingredient: LiveData<List<Ingredient>>
        get() = _ingredient


    var _stepData = MutableLiveData<List<Step>>()

    val stepData: LiveData<List<Step>>
        get() = _stepData




    fun getIngredient(recipes: Recipes){
        _ingredient.value = recipes.ingredient
        Log.d("hank1","這欄的size是 ${recipes.ingredient.size}")

    }

    fun getStep(recipes: Recipes){
        _stepData.value = recipes.step
        Log.d("hank1","這欄的size是 ${recipes.step.size}")

    }



}