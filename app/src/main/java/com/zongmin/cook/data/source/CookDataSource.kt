package com.zongmin.cook.data.source

import androidx.lifecycle.MutableLiveData
import com.zongmin.cook.data.*

interface CookDataSource {

    suspend fun getRecipes():Result<List<Recipes>>

//    suspend fun getIngredient():Result<List<Ingredient>>


    suspend fun getPlan():Result<List<Plan>>


    suspend fun getManagement(): Result<List<Management>>

    suspend fun getUser(): Result<User>

}
