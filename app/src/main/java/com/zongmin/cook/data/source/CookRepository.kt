package com.zongmin.cook.data.source


import com.zongmin.cook.data.*

interface CookRepository {

    suspend fun getRecipes(collect: List<String>): Result<List<Recipes>>

    suspend fun getCategoryRecipes(collect: List<String>, type: String): Result<List<Recipes>>

    suspend fun getCompoundRecipes(collect: List<String>, type: String, key: String): Result<List<Recipes>>

    suspend fun getKeywordRecipes(collect: List<String>, key: String): Result<List<Recipes>>

    suspend fun getCreationRecipes(userId: String): Result<List<Recipes>>
//    suspend fun getIngredient(): Result<List<Ingredient>>

    suspend fun getCollectRecipes(collect: List<String>): Result<List<Recipes>>


    suspend fun getPlan(): Result<List<Plan>>


    suspend fun getManagement(): Result<List<Management>>

    suspend fun getUser(id: String): Result<User>

    suspend fun createRecipes(summary: Summary,ingredient: List<Ingredient>,step: List<Step>): Result<Boolean>

    suspend fun userSignIn(user: User): Result<Boolean>

    suspend fun deleteRecipes(id: String): Result<Boolean>

    suspend fun createPlan(plan: Plan): Result<Boolean>

    suspend fun deletePlan(id: String): Result<Boolean>

}