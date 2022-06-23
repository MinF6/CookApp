package com.zongmin.cook.data.source

import android.util.Log
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result

class DefaultCookRepository(
    private val cookRemoteDataSource: CookDataSource,
) : CookRepository {
    override suspend fun getRecipes(): Result<List<Recipes>> {

        return cookRemoteDataSource.getRecipes()
    }

    override suspend fun getPlan(): Result<List<Plan>> {

        return cookRemoteDataSource.getPlan()
    }

//    override suspend fun getIngredient(): Result<List<Ingredient>> {
//        return cookRemoteDataSource.getIngredient()
//    }


}
