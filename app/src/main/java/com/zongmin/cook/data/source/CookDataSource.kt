package com.zongmin.cook.data.source

import androidx.lifecycle.MutableLiveData
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result

interface CookDataSource {

    suspend fun getRecipes():Result<List<Recipes>>

//    suspend fun getIngredient():Result<List<Ingredient>>



}
