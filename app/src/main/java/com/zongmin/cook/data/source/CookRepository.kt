package com.zongmin.cook.data.source


import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result

interface CookRepository {

    suspend fun getRecipes(): Result<List<Recipes>>


//    suspend fun getIngredient(): Result<List<Ingredient>>


    suspend fun getPlan(): Result<List<Plan>>

}