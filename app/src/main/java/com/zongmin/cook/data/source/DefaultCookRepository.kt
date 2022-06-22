package com.zongmin.cook.data.source

import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result

class DefaultCookRepository(
    private val cookRemoteDataSource: CookDataSource,
) : CookRepository {
    override suspend fun getRecipes(): Result<List<Recipes>> {

        return cookRemoteDataSource.getRecipes()
    }

//    override suspend fun getIngredient(): Result<List<Ingredient>> {
//        return cookRemoteDataSource.getIngredient()
//    }


}
