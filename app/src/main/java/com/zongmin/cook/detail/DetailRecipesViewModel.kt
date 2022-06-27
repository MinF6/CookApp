package com.zongmin.cook.detail

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

    }

    fun getSetp(recipes: Recipes){
        _stepData.value = recipes.step

    }



}