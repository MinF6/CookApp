package com.zongmin.cook.data.source

import androidx.lifecycle.MutableLiveData
import com.zongmin.cook.data.*

interface CookDataSource {

    suspend fun getRecipes(): Result<List<Recipes>>

    suspend fun getCategoryRecipes(type: String): Result<List<Recipes>>

    suspend fun getCompoundRecipes(type: String, key: String): Result<List<Recipes>>

    suspend fun getKeywordRecipes(key: String): Result<List<Recipes>>

//    suspend fun getIngredient():Result<List<Ingredient>>


    suspend fun getPlan(): Result<List<Plan>>


    suspend fun getManagement(): Result<List<Management>>

    suspend fun getUser(): Result<User>

    suspend fun createRecipes(summary: Summary,ingredient: List<Ingredient>,step: List<Step>): Result<Boolean>

    suspend fun userSignIn(user: User): Result<Boolean>

    suspend fun deleteRecipes(id: String): Result<Boolean>

    suspend fun createPlan(plan: Plan): Result<Boolean>

    suspend fun deletePlan(id: String): Result<Boolean>

}
