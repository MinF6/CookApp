package com.zongmin.cook.data.source

import com.zongmin.cook.data.*

class DefaultCookRepository(
    private val cookRemoteDataSource: CookDataSource,
) : CookRepository {
    override suspend fun getRecipes(collect: List<String>): Result<List<Recipe>> {

        return cookRemoteDataSource.getRecipes(collect)
    }

    override suspend fun getCategoryRecipes(collect: List<String>, type: String): Result<List<Recipe>> {

        return cookRemoteDataSource.getCategoryRecipes(collect, type)
    }

    override suspend fun getCompoundRecipes(collect: List<String>, type: String, key: String): Result<List<Recipe>> {

        return cookRemoteDataSource.getCompoundRecipes(collect, type, key)
    }

    override suspend fun getKeywordRecipes(collect: List<String>, key: String): Result<List<Recipe>> {

        return cookRemoteDataSource.getKeywordRecipes(collect, key)
    }

    override suspend fun getCreationRecipes(userId: String): Result<List<Recipe>> {

        return cookRemoteDataSource.getCreationRecipes(userId)
    }

    override suspend fun getCollectRecipes(collect: List<String>): Result<List<Recipe>> {

        return cookRemoteDataSource.getCollectRecipes(collect)
    }

    override suspend fun getPublicRecipes(): Result<List<Recipe>> {

        return cookRemoteDataSource.getPublicRecipes()
    }

    override suspend fun getPlan(userId: String, time: Long): Result<List<Plan>> {

        return cookRemoteDataSource.getPlan(userId, time)
    }

    override suspend fun getManagement(userId: String, time: Long): Result<List<Management>> {

        return cookRemoteDataSource.getManagement(userId, time)
    }

    override suspend fun getSpecifyManagement(planId: String): Result<List<Management>> {

        return cookRemoteDataSource.getSpecifyManagement(planId)
    }

    override suspend fun getPeriodManagement(
        userId: String,
        todayTime: Long,
        scopeTime: Long
    ): Result<List<Management>> {
        return cookRemoteDataSource.getPeriodManagement(userId, todayTime, scopeTime)
    }

    override suspend fun getUser(id: String): Result<User> {

        return cookRemoteDataSource.getUser(id)
    }

    override suspend fun getSocialUser(userList: List<String>): Result<List<User>> {

        return cookRemoteDataSource.getSocialUser(userList)
    }

    override suspend fun getFollowList(userList: List<String>): Result<List<User>> {

        return cookRemoteDataSource.getFollowList(userList)
    }

    override suspend fun createRecipes(
        summary: Summary,
        ingredient: List<Ingredient>,
        step: List<Step>
    ): Result<String> {

        return cookRemoteDataSource.createRecipes(summary, ingredient, step)
    }

    override suspend fun userSignIn(user: User): Result<Boolean> {

        return cookRemoteDataSource.userSignIn(user)
    }

    override suspend fun deleteRecipes(id: String): Result<Boolean> {

        return cookRemoteDataSource.deleteRecipes(id)
    }

    override suspend fun createPlan(plan: Plan): Result<String> {

        return cookRemoteDataSource.createPlan(plan)
    }

    override suspend fun deletePlan(id: String): Result<String> {

        return cookRemoteDataSource.deletePlan(id)
    }

    override suspend fun createManagement(management: Management): Result<Boolean> {

        return cookRemoteDataSource.createManagement(management)
    }

    override suspend fun deleteManagement(id: String): Result<Boolean> {

        return cookRemoteDataSource.deleteManagement(id)
    }

    override suspend fun setCollect(isCollect: Boolean, recipesId: String): Result<Boolean> {

        return cookRemoteDataSource.setCollect(isCollect, recipesId)
    }

    override suspend fun setLike(isLiked: Boolean, recipesId: String): Result<Boolean> {

        return cookRemoteDataSource.setLike(isLiked, recipesId)
    }

    override suspend fun setPublic(isPublic: Boolean, recipesId: String): Result<Boolean> {

        return cookRemoteDataSource.setPublic(isPublic, recipesId)
    }

    override suspend fun setPrepare(isPrepare: Boolean, managementId: String): Result<Boolean> {

        return cookRemoteDataSource.setPrepare(isPrepare, managementId)
    }


}
