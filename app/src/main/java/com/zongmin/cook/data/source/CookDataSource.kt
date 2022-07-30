package com.zongmin.cook.data.source

import com.zongmin.cook.data.*

interface CookDataSource {

    suspend fun getRecipes(collect: List<String>): Result<List<Recipe>>

    suspend fun getCategoryRecipes(collect: List<String>, type: String): Result<List<Recipe>>

    suspend fun getCompoundRecipes(collect: List<String>, type: String, key: String): Result<List<Recipe>>

    suspend fun getKeywordRecipes(collect: List<String>, key: String): Result<List<Recipe>>

    //改版，根據使用者ID取得使用者創作的食譜
    suspend fun getCreationRecipes(userId: String): Result<List<Recipe>>

    //取得使用者有收藏的食譜
    suspend fun getCollectRecipes(collect: List<String>): Result<List<Recipe>>

    suspend fun getPublicRecipes(): Result<List<Recipe>>

//    suspend fun getIngredient():Result<List<Ingredient>>

    suspend fun getPlan(userId: String, time: Long): Result<List<Plan>>

    suspend fun getManagement(userId: String, time: Long): Result<List<Management>>

    suspend fun getSpecifyManagement(planId: String): Result<List<Management>>

    suspend fun getPeriodManagement(userId: String, todayTime: Long, scopeTime: Long): Result<List<Management>>

    suspend fun getUser(id: String): Result<User>

    suspend fun getSocialUser(userList: List<String>): Result<List<User>>

    suspend fun getFollowList(userList: List<String>): Result<List<User>>

    suspend fun createRecipes(summary: Summary,ingredient: List<Ingredient>,step: List<Step>): Result<String>

    suspend fun userSignIn(user: User): Result<Boolean>

    suspend fun deleteRecipes(id: String): Result<Boolean>

    suspend fun createPlan(plan: Plan): Result<String>

    suspend fun deletePlan(id: String): Result<String>

    suspend fun createManagement(management: Management): Result<Boolean>

    suspend fun deleteManagement(id: String): Result<Boolean>

    suspend fun setCollect(isCollect: Boolean, recipesId: String): Result<Boolean>

    suspend fun setLike(isLiked: Boolean, recipesId: String): Result<Boolean>

    suspend fun setPublic(isPublic:Boolean, recipesId: String): Result<Boolean>

    suspend fun setPrepare(isPrepare: Boolean, managementId: String): Result<Boolean>
}
