package com.zongmin.cook.data.source

import com.zongmin.cook.data.*

class DefaultCookRepository(
    private val cookRemoteDataSource: CookDataSource,
) : CookRepository {
    override suspend fun getRecipes(collect: List<String>): Result<List<Recipes>> {

        return cookRemoteDataSource.getRecipes(collect)
    }

    override suspend fun getCategoryRecipes(collect: List<String>, type: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getCategoryRecipes(collect, type)
    }

    override suspend fun getCompoundRecipes(collect: List<String>, type: String, key: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getCompoundRecipes(collect, type, key)
    }

    override suspend fun getKeywordRecipes(collect: List<String>, key: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getKeywordRecipes(collect, key)
    }

    override suspend fun getCreationRecipes(userId: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getCreationRecipes(userId)
    }

    override suspend fun getCollectRecipes(collect: List<String>): Result<List<Recipes>> {

        return cookRemoteDataSource.getCollectRecipes(collect)
    }

    override suspend fun getPublicRecipes(): Result<List<Recipes>> {

        return cookRemoteDataSource.getPublicRecipes()
    }

    override suspend fun getPlan(userId: String, time: Long): Result<List<Plan>> {

        return cookRemoteDataSource.getPlan(userId, time)
    }

    override suspend fun getManagement(userId: String): Result<List<Management>> {

        return cookRemoteDataSource.getManagement(userId)
    }

    override suspend fun getUser(id: String): Result<User> {

        return cookRemoteDataSource.getUser(id)
    }

    override suspend fun getSocialUser(userList: List<String>): Result<List<User>> {

        return cookRemoteDataSource.getSocialUser(userList)
    }

    override suspend fun createRecipes(
        summary: Summary,
        ingredient: List<Ingredient>,
        step: List<Step>
    ): Result<Boolean> {

        return cookRemoteDataSource.createRecipes(summary, ingredient, step)
    }

    override suspend fun userSignIn(user: User): Result<Boolean> {

        return cookRemoteDataSource.userSignIn(user)
    }

    override suspend fun deleteRecipes(id: String): Result<Boolean> {

        return cookRemoteDataSource.deleteRecipes(id)
    }

    override suspend fun createPlan(plan: Plan): Result<Boolean> {

        return cookRemoteDataSource.createPlan(plan)
    }

    override suspend fun deletePlan(id: String): Result<Boolean> {

        return cookRemoteDataSource.deletePlan(id)
    }

//    override suspend fun getIngredient(): Result<List<Ingredient>> {
//        return cookRemoteDataSource.getIngredient()
//    }


}
