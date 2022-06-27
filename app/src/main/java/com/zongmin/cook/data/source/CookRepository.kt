package com.zongmin.cook.data.source


import com.zongmin.cook.data.*

interface CookRepository {

    suspend fun getRecipes(): Result<List<Recipes>>


//    suspend fun getIngredient(): Result<List<Ingredient>>


    suspend fun getPlan(): Result<List<Plan>>


    suspend fun getManagement(): Result<List<Management>>

    suspend fun getUser(): Result<User>

}