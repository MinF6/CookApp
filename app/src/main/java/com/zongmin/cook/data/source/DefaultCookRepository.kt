package com.zongmin.cook.data.source

import android.util.Log
import com.zongmin.cook.data.*

class DefaultCookRepository(
    private val cookRemoteDataSource: CookDataSource,
) : CookRepository {
    override suspend fun getRecipes(): Result<List<Recipes>> {

        return cookRemoteDataSource.getRecipes()
    }

    override suspend fun getCategoryRecipes(type: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getCategoryRecipes(type)
    }

    override suspend fun getCompoundRecipes(type: String, key: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getCompoundRecipes(type, key)
    }

    override suspend fun getKeywordRecipes(key: String): Result<List<Recipes>> {

        return cookRemoteDataSource.getKeywordRecipes(key)
    }

    override suspend fun getPlan(): Result<List<Plan>> {

        return cookRemoteDataSource.getPlan()
    }

    override suspend fun getManagement(): Result<List<Management>> {

        return cookRemoteDataSource.getManagement()
    }

    override suspend fun getUser(): Result<User> {

        return cookRemoteDataSource.getUser()
    }

//    override suspend fun getIngredient(): Result<List<Ingredient>> {
//        return cookRemoteDataSource.getIngredient()
//    }


}
